package com.hjb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hjb.domain.param.GoodsInfoParam;
import com.hjb.domain.po.GoodsInfo;
import com.hjb.elastic.model.EsGoods;
import com.hjb.elastic.model.Query;
import com.hjb.util.Result;

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
     * 创建商品
     * @param goodsInfoParam
     * @return
     */
    Result save(GoodsInfoParam goodsInfoParam);

    /**
     * 查询商品详情
     * @param id
     * @return
     */
    Result detail(Long id);

    /**
     *
     * @param ids
     * @return
     */
    Boolean deleteGoods(List<Long> ids);

    /**
     * es查询
     * @param query
     * @return
     */
    List<EsGoods> query(Query query);
}
