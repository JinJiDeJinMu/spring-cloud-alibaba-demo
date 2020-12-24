package com.hjb.service;

import com.hjb.domain.dto.CartVo;
import com.hjb.domain.CartItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinmu
 * @since 2020-12-09
 */
public interface CartItemService extends IService<CartItem> {

    /**
     * 添加购物车
     * @param skuId
     * @return
     */
    boolean addCart(Long skuId, Long number);

    /**
     * 删除购物车商品
     * @param ids
     * @return
     */
    boolean deleteCarts(List<Long> ids);


    /**
     * 获取用户的购物车
     * @param userId
     * @return
     */
    List<CartVo> getCart(Long userId);

}
