package com.hjb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjb.domain.po.Order;
import com.hjb.mapper.OrderMapper;
import com.hjb.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单服务实现类
 * </p>
 *
 * @author jinmu
 * @since 2020-11-23
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
