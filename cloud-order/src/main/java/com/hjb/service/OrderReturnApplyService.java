package com.hjb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hjb.domain.param.OrderReturnParam;
import com.hjb.domain.po.OrderReturnApply;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jinmu
 * @since 2020-12-30
 */
public interface OrderReturnApplyService extends IService<OrderReturnApply> {

    boolean createOrderReturn(OrderReturnParam orderReturnParam);

    boolean orderExpress(Long id, String expressCode);

    boolean updateOrderReturn(Long id, Integer status);
}
