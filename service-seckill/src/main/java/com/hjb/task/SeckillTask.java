package com.hjb.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hjb.constant.SkillRedisConstant;
import com.hjb.domain.dto.SeckillActivityDTO;
import com.hjb.domain.po.SeckillActivity;
import com.hjb.domain.po.SeckillGoods;
import com.hjb.service.SeckillActivityService;
import com.hjb.service.SeckillGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class SeckillTask {

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    @Autowired
    private SeckillActivityService seckillActivityService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 定时把活动加入到缓存中
     */
    @Scheduled(cron = "0 */30 * * * ?")
    public void handle(){
        log.info("to redis time ={}",LocalDateTime.now());

        Date now = new Date();
        //查询所有没上架的活动

        List<SeckillActivity> activityList = seckillActivityService.list(new LambdaQueryWrapper<SeckillActivity>()
                .eq(SeckillActivity::getStatus, 0)
                .eq(SeckillActivity::getIsCache, 0));


        activityList.stream().forEach(e->{
            List<SeckillGoods> goodsList = seckillGoodsService.list(new LambdaQueryWrapper<SeckillGoods>()
                    .eq(SeckillGoods::getActivityId, e.getId()));

            goodsList.stream().forEach(x->{

                String key = SkillRedisConstant.KILL + SkillRedisConstant.ACTIVITY_ID + e.getId() + SkillRedisConstant.SKU_ID + x.getSkuId();

                if(!redisTemplate.hasKey(key)){
                    SeckillActivityDTO seckillActivityDTO = new SeckillActivityDTO();
                    BeanUtils.copyProperties(x,seckillActivityDTO);
                    seckillActivityDTO.setStartTime(e.getStartTime());
                    seckillActivityDTO.setEndTime(e.getEndTime());

                    Map map = JSONObject.parseObject(JSONObject.toJSONString(seckillActivityDTO), Map.class);

                    long ttl = e.getEndTime().getTime()- now.getTime() + 100;
                    String hashKey = SkillRedisConstant.SEMPLATE + SkillRedisConstant.SKU_ID + x.getSkuId();

                    //设置库存信号量
                    RSemaphore semaphore = redissonClient.getSemaphore(hashKey);
                    semaphore.trySetPermits(x.getSkillCount().intValue());
                    //保存秒杀商品信息
                    redisTemplate.opsForHash().putAll(key,map);
                    redisTemplate.expire(key,ttl, TimeUnit.MILLISECONDS);
                }
            });
        });
    }


    @Scheduled(cron = "0 * */1 * * ?")
    public void changeStatus(){

        log.info("change status time={}",LocalDateTime.now());
        List<SeckillActivity> activityList = seckillActivityService.list(new LambdaQueryWrapper<SeckillActivity>()
                .eq(SeckillActivity::getStatus, 0)
                .ge(SeckillActivity::getEndTime ,new Date()));

        activityList.forEach(e->{
            e.setStatus(1);
            seckillActivityService.save(e);
        });
    }
}
