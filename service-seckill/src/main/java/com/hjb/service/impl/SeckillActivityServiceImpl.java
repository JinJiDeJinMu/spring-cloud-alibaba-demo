package com.hjb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.hjb.constant.SkillRedisConstant;
import com.hjb.domain.dto.SeckillActivityDTO;
import com.hjb.domain.dto.SkillMessage;
import com.hjb.domain.param.ActivityParam;
import com.hjb.domain.po.SeckillActivity;
import com.hjb.domain.po.SeckillGoods;
import com.hjb.mapper.SeckillActivityMapper;
import com.hjb.service.SeckillActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjb.service.SeckillGoodsService;
import com.hjb.util.SecurityUserUtils;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jinmu
 * @since 2020-12-29
 */
@Service
public class SeckillActivityServiceImpl extends ServiceImpl<SeckillActivityMapper, SeckillActivity> implements SeckillActivityService {

    @Autowired
    private SecurityUserUtils securityUserUtils;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    @Autowired
    private SeckillActivityMapper seckillActivityMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private DefaultMQProducer producer;

    @Override
    public boolean createdSeckillActivity(ActivityParam activityParam) {
        SeckillActivity seckillActivity = new SeckillActivity();

        BeanUtils.copyProperties(activityParam,seckillActivity);
        seckillActivity.setStatus(0);
        seckillActivity.setCreateTime(new Date());
        seckillActivity.setCreateUserId(Long.valueOf(securityUserUtils.getUserInfo().get("id").toString()));

        this.save(seckillActivity);

        return true;
    }

    @Override
    public boolean stopActivity(Long id) {
        SeckillActivity activity = this.getById(id);

        if(activity == null){
            throw new RuntimeException("商品不存在");
        }
        activity.setStatus(1);
        this.save(activity);

        return true;
    }

    @Override
    public boolean syncActivity(Long activityId, Long secKillSkuId) {

        SeckillActivity activity = this.getById(activityId);
        Date now = new Date();

        String key = SkillRedisConstant.KILL + SkillRedisConstant.ACTIVITY_ID + activityId + SkillRedisConstant.SKU_ID + secKillSkuId;
        if(redisTemplate.hasKey(key)){
            return false;
        }

        SeckillGoods seckillGoods = seckillGoodsService.getOne(new LambdaQueryWrapper<SeckillGoods>()
                .eq(SeckillGoods::getActivityId, activityId)
                .eq(SeckillGoods::getSkuId, secKillSkuId));

        SeckillActivityDTO seckillActivityDTO = new SeckillActivityDTO();
        BeanUtils.copyProperties(seckillGoods,seckillActivityDTO);
        seckillActivityDTO.setStartTime(activity.getStartTime());
        seckillActivityDTO.setEndTime(activity.getEndTime());

        Map map = JSONObject.parseObject(JSONObject.toJSONString(seckillActivityDTO), Map.class);

        long ttl = activity.getEndTime().getTime()- now.getTime() + 100;
        String hashKey = SkillRedisConstant.SEMPLATE + SkillRedisConstant.SKU_ID + secKillSkuId;

        //设置库存信号量
        RSemaphore semaphore = redissonClient.getSemaphore(hashKey);
        semaphore.trySetPermits(seckillGoods.getSkillCount().intValue());
        //保存秒杀商品信息
        redisTemplate.opsForHash().putAll(key,map);
        redisTemplate.expire(key,ttl,TimeUnit.MILLISECONDS);

        return true;
    }

    @Override
    public List<SeckillActivityDTO> skillList() {
        return seckillActivityMapper.sillList();
    }

    /**
     * 秒杀商品
     * @param activityId
     * @param skuId
     * @param num
     * @return
     */
    @Override
    public String kill(Long activityId, Long skuId, Integer num,String path) {
        if(!redisTemplate.opsForValue().get("kill-url" + activityId).toString().equals(path)){
            return null;
        }
        String hashKey = SkillRedisConstant.KILL + SkillRedisConstant.ACTIVITY_ID + activityId + SkillRedisConstant.SKU_ID + skuId;
        if(!redisTemplate.hasKey(hashKey)){
            return null;
        }
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(hashKey);
        SeckillActivityDTO activityDTO= BeanUtil.fillBeanWithMap(entries, new SeckillActivityDTO(), false);

        Date now = new Date();
        if(activityDTO != null){
            //判断时间
            if(now.before(activityDTO.getEndTime()) && now.after(activityDTO.getStartTime())){
                //判断数量
                if(num <=activityDTO.getSkillLimitCount()){
                    //判断是否购买
                    long ttl = activityDTO.getEndTime().getTime() - now.getTime();
                    String userKey = SkillRedisConstant.USER_ID + securityUserUtils.getUserInfo().get("id") + SkillRedisConstant.SKU_ID + skuId;
                    Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(userKey, num, ttl, TimeUnit.MILLISECONDS);
                    if(aBoolean){
                        //减少信号量
                        RSemaphore semaphore = redissonClient.getSemaphore(SkillRedisConstant.SEMPLATE + SkillRedisConstant.SKU_ID + skuId);
                        try {
                            boolean acquire = semaphore.tryAcquire(num, 100, TimeUnit.MILLISECONDS);
                            if(acquire){
                                //下单发送到MQ，返回订单号
                                long id = IdUtil.getSnowflake(1, 1).nextId();
                                SkillMessage message = new SkillMessage();

                                message.setOrderSn(id);
                                message.setNum(num);
                                message.setUserId(Long.valueOf(securityUserUtils.getUserInfo().get("id").toString()));
                                message.setSkillPrice(activityDTO.getSkillPrice());
                                message.setSkuId(skuId);

                                Message msg = new Message();
                                msg.setTopic("order-kill");
                                msg.setTags("*");
                                msg.setBody(JSONObject.toJSONBytes(message));

                                producer.send(msg);

                                return String.valueOf(id);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (RemotingException e) {
                            e.printStackTrace();
                        } catch (MQClientException e) {
                            e.printStackTrace();
                        } catch (MQBrokerException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String killUrl(Long activityId) {
        String url = Hashing.md5().newHasher().putString(activityId.toString(), Charsets.UTF_8).hash().toString();

        redisTemplate.opsForValue().set("kill-url-" + activityId, url,6000,TimeUnit.SECONDS);

        return url;
    }

}
