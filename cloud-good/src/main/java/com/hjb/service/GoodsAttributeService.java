package com.hjb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hjb.domain.GoodsAttribute;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinmu
 * @since 2020-11-25
 */
public interface GoodsAttributeService extends IService<GoodsAttribute> {

    Boolean deleteGoodsAttrByGoodsId(List<Long> goodsIds);
}
