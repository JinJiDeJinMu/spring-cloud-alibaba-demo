package com.hjb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hjb.domain.param.OrderParam;
import com.hjb.domain.po.Order;
import com.hjb.util.Result;

/**
 * <p>
 * 订单服务类
 * </p>
 *
 * @author jinmu
 * @since 2020-11-23
 */
public interface OrderService extends IService<Order> {


    /**
     * 商品购买前校验
     * @return
     */
    Result buyConfirm(OrderParam orderParam);

    /**
     * 提交订单
     * @param orderParam
     * @return
     */
    Result submit(OrderParam orderParam);


    boolean orderPaySuccess(Order order);

    /**
     * 订单发货
     * @param id
     * @param deliveryCompany
     * @param deliverySn
     * @return
     */
    boolean orderSendGoods(Long id, String deliveryCompany, String deliverySn);
}
