package com.hjb.mq.consume;

import com.alibaba.fastjson.JSONObject;
import com.hjb.constant.OrderStatusConstans;
import com.hjb.domain.dto.GoodsDetailDTO;
import com.hjb.domain.po.Order;
import com.hjb.feign.GoodsFeignService;
import com.hjb.mq.annotation.MQConsumeService;
import com.hjb.mq.common.MQConsumeResult;
import com.hjb.mq.processor.AbstractMQMsgProcessor;
import com.hjb.service.OrderService;
import com.hjb.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单提交队列消费端处理
 */
@Slf4j
@MQConsumeService(topic = "order-pay", tags = "*")
@Component
public class OrderConsumeMsgProcessor extends AbstractMQMsgProcessor {

    @Autowired
    private GoodsFeignService goodsFeignService;

    @Autowired
    private OrderService orderService;

    @Override
    protected MQConsumeResult consumeMessage(String tag, MessageExt messageExt) {
        MQConsumeResult result = new MQConsumeResult();
        //TODO 判断该消息是否重复消费（RocketMQ不保证消息不重复，如果你的业务需要保证严格的不重复消息，需要你自己在业务端去重）
        JSONObject msg = JSONObject.parseObject(new String(messageExt.getBody()));
        long orderId = msg.getLong("orderId");

        Order order = orderService.getById(orderId);

        if(order == null){
            log.info("orderId = {} is not exist", orderId);
            result.setSuccess(false);

        }
        //订单已支付
        if(order.getStatus() == OrderStatusConstans.WAIT_SEND_GOODS.getCode()){
            log.info("orderId = {} status is change", orderId);
            result.setSuccess(true);
        }

        //更新订单状态
        boolean flag = orderService.orderPaySuccess(order);
        if(flag == false){
            result.setSuccess(false);
        }
        //用户积分
        //一些其他操作等等

        return result;
    }
}
