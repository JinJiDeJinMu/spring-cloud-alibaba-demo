package com.hjb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjb.domain.GoodsInfo;
import com.hjb.domain.SkuInfo;
import com.hjb.elastic.EsService;
import com.hjb.elastic.model.Goods;
import com.hjb.mapper.SkuInfoMapper;
import com.hjb.service.GoodsInfoService;
import com.hjb.service.SkuInfoService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jinmu
 * @since 2020-11-20
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private EsService esService;

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Override
    public boolean deleteSKUByGoodsId(List<Long> goodsIds) {
        return skuInfoMapper.deleteSKUByGoodsId(goodsIds);
    }

    @Override
    public boolean addOrUpdateSkuInfo(SkuInfo skuInfo) {
        saveOrUpdate(skuInfo);

        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean reduceSKUCount(Long skuId, Long number) {

        RLock lock = redissonClient.getLock("skuId-" + skuId + "-count-lock");
        try {
            lock.lock();
            SkuInfo skuInfo = getById(skuId);
            if(skuInfo == null){
                return Boolean.FALSE;
            }
            if(skuInfo.getMount() >= number){
                skuInfo.setMount(skuInfo.getMount()- number);
                skuInfo.setSaleCount(skuInfo.getSaleCount() + number);
            }
            this.saveOrUpdate(skuInfo);
        }catch (Exception e){

        }finally {
            lock.unlock();
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addSKUCount(Long skuId, Long number) {
        RLock lock = redissonClient.getLock("skuId-" + skuId + "-count-lock");
        try {
            lock.lock();
            SkuInfo skuInfo = getById(skuId);
            if(skuInfo == null){
                return Boolean.FALSE;
            }
            skuInfo.setSaleCount((skuInfo.getSaleCount() - number) <0 ? 0 : skuInfo.getSaleCount() - number);
            skuInfo.setMount(skuInfo.getMount() + number);

            this.saveOrUpdate(skuInfo);
        }catch (Exception e){

        }finally {
            lock.unlock();
        }
        return true;
    }
}
