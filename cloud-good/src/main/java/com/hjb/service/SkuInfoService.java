package com.hjb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hjb.domain.po.SkuInfo;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinmu
 * @since 2020-11-20
 */
public interface SkuInfoService extends IService<SkuInfo> {

    boolean deleteSKUByGoodsId(@Param("goodsId") List<Long> goodsIds);
}
