package com.hjb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hjb.domain.dto.GoodsDetailDTO;
import com.hjb.domain.param.GoodsInfoParam;
import com.hjb.domain.po.GoodsAttr;
import com.hjb.domain.po.GoodsInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinmu
 * @since 2020-11-20
 */
public interface GoodsInfoService extends IService<GoodsInfo> {

    /**
     * 添加商品
     * @param goodsInfoParam
     * @return
     */
    Boolean save(GoodsInfoParam goodsInfoParam);

    GoodsDetailDTO goodsDetail(Long id);

    /**
     * 查询指定商品的所有规格属性
     * @param id
     * @return
     */
    String goodsAttrs(Long id);
}
