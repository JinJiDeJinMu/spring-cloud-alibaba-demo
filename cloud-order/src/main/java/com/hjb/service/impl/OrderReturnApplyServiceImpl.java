package com.hjb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjb.constant.OrderReturnConstans;
import com.hjb.constant.OrderStatusConstans;
import com.hjb.domain.param.OrderReturnParam;
import com.hjb.domain.po.Order;
import com.hjb.domain.po.OrderReturnApply;
import com.hjb.mapper.OrderReturnApplyMapper;
import com.hjb.service.OrderReturnApplyService;
import com.hjb.service.OrderService;
import com.hjb.util.SecurityUserUtils;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jinmu
 * @since 2020-12-30
 */
@Service
public class OrderReturnApplyServiceImpl extends ServiceImpl<OrderReturnApplyMapper, OrderReturnApply> implements OrderReturnApplyService {

    @Autowired
    private SecurityUserUtils securityUserUtils;

    @Autowired
    private OrderService orderService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean createOrderReturn(OrderReturnParam orderReturnParam) {

        OrderReturnApply orderReturnApply = new OrderReturnApply();
        BeanUtils.copyProperties(orderReturnParam, orderReturnApply);

        orderReturnApply.setUserId(Long.valueOf(securityUserUtils.getUserInfo().get("id").toString()));

        Integer status = OrderReturnConstans.getStatusByType(orderReturnParam.getType());
        orderReturnApply.setStatus(status);
        orderReturnApply.setCreateTime(LocalDateTime.now());
        orderReturnApply.setUpdateTime(LocalDateTime.now());

        this.save(orderReturnApply);

        //更新订单状态
        updateOrder(orderReturnApply.getOrderId(),status);

        return true;
    }

    /**
     * 发货
     * @param id
     * @param expressCode
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean orderExpress(Long id, String expressCode) {
        OrderReturnApply returnApply = this.getById(id);
        if(returnApply == null){
            return false;
        }
        returnApply.setStatus(OrderStatusConstans.GOOD_SENDING.getCode());
        returnApply.setExpressCode(expressCode);
        returnApply.setUpdateTime(LocalDateTime.now());

        this.save(returnApply);
        //更新订单状态
        updateOrder(returnApply.getOrderId(),OrderStatusConstans.GOOD_SENDING.getCode());

        return true;
    }

    @Override
    public boolean updateOrderReturn(Long id, Integer status) {
        OrderReturnApply returnApply = this.getById(id);
        if(returnApply == null){
            return false;
        }
        returnApply.setStatus(status);
        returnApply.setUpdateTime(LocalDateTime.now());

        //更新订单状态
        updateOrder(returnApply.getOrderId(),status);

        return true;
    }

    public boolean updateOrder(Long orderId, Integer status){
        Order order = orderService.getById(orderId);
        if(order == null){
            return false;
        }
        order.setStatus(status);
        order.setModifyTime(new Date());

        orderService.saveOrUpdate(order);

        return true;
    }
}
