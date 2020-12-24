package com.hjb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hjb.domain.dto.GoodsDetailDTO;
import com.hjb.domain.param.GoodsInfoParam;
import com.hjb.domain.GoodsInfo;
import com.hjb.util.Result;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinmu
 * @since 2020-11-20
 */
@Transactional(rollbackFor = Exception.class)
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
    GoodsDetailDTO detail(Long id);

    /**
     *
     * @param ids
     * @return
     */
    Boolean deleteGoods(List<Long> ids);

}
