package com.hjb.mq.consume;

import com.alibaba.fastjson.JSONObject;
import com.hjb.domain.po.Order;
import com.hjb.mq.annotation.MQConsumeService;
import com.hjb.mq.common.MQConsumeResult;
import com.hjb.mq.processor.AbstractMQMsgProcessor;
import com.hjb.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 秒杀订单处理
 */
@Slf4j
@MQConsumeService(topic = "order-kill", tags = "*")
@Component
public class KillOrderConsumeMsgProcessor extends AbstractMQMsgProcessor {

    @Autowired
    private OrderService orderService;

    @Override
    protected MQConsumeResult consumeMessage(String tag, MessageExt messageExt) {

        String msg = JSONObject.toJSONString(new String(messageExt.getBody()));
        log.info("收到秒杀订单,,msg={}", msg);

        MQConsumeResult result = new MQConsumeResult();
        result.setSuccess(true);
        return result;
    }

}
