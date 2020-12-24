package com.hjb.mq.consume;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hjb.constant.OrderStatusConstans;
import com.hjb.domain.Order;
import com.hjb.domain.OrderItem;
import com.hjb.feign.GoodsFeignService;
import com.hjb.mq.annotation.MQConsumeService;
import com.hjb.mq.common.MQConsumeResult;
import com.hjb.mq.processor.AbstractMQMsgProcessor;
import com.hjb.service.OrderItemService;
import com.hjb.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 订单超时自动取消
 */
@Slf4j
@MQConsumeService(topic = "order_cancel", tags = "*")
@Component
public class OrderCancelConsumeMsgProcessor extends AbstractMQMsgProcessor {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private GoodsFeignService goodsFeignService;

    @Override
    protected MQConsumeResult consumeMessage(String tag, MessageExt messageExt) {

        Long orderId = JSONObject.parseObject(new String(messageExt.getBody()),Long.class);

        if(orderId != null && orderId != 0) {
            Order checkOrder = orderService.getById(orderId);
            if(checkOrder.getStatus() == 0){
                //关闭订单
                checkOrder.setStatus(OrderStatusConstans.CLOSED_ORDER.getCode());
                checkOrder.setModifyTime(new Date());

                boolean flag = orderService.saveOrUpdate(checkOrder);
                if(flag){
                    //解锁库存
                    List<OrderItem> orderItems = orderItemService.list(new LambdaQueryWrapper<OrderItem>()
                    .eq(OrderItem::getOrderId,orderId));

                    orderItems.stream().forEach(e->{
                        goodsFeignService.addSkuCount(e.getSkuId(),Long.valueOf(e.getSkuQuantity()));
                    });
                }
                log.info("订单自动关闭, order :{}" ,checkOrder);
            }
        }
        MQConsumeResult result = new MQConsumeResult();
        result.setSuccess(true);
        return result;
    }
}
