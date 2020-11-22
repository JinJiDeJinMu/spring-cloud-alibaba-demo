package com.hjb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hjb.domain.po.GoodsAttr;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinmu
 * @since 2020-11-20
 */
public interface GoodsAttrService extends IService<GoodsAttr> {

    Boolean deleteGoodsAttrByGoodsId(List<Long> goodsIds);
}
