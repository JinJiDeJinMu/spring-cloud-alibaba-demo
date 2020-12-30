package com.hjb.mq.consume;

import com.alibaba.fastjson.JSONObject;
import com.hjb.domain.dto.GoodsDetailDTO;
import com.hjb.feign.GoodsFeignService;
import com.hjb.mq.annotation.MQConsumeService;
import com.hjb.mq.common.MQConsumeResult;
import com.hjb.mq.processor.AbstractMQMsgProcessor;
import com.hjb.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单提交队列消费端处理
 */
@Slf4j
@MQConsumeService(topic = "order-submit", tags = "*")
@Component
public class OrderConsumeMsgProcessor extends AbstractMQMsgProcessor {

    @Autowired
    private GoodsFeignService goodsFeignService;

    @Override
    protected MQConsumeResult consumeMessage(String tag, MessageExt messageExt) {

        //TODO 判断该消息是否重复消费（RocketMQ不保证消息不重复，如果你的业务需要保证严格的不重复消息，需要你自己在业务端去重）

        JSONObject msg = JSONObject.parseObject(new String(messageExt.getBody()));
        long goodsId = msg.getLong("goodsId");
        GoodsDetailDTO goodsDetail =goodsFeignService.goodsDetail(goodsId);

        System.out.println("sssss="+goodsDetail);

        MQConsumeResult result = new MQConsumeResult();
        result.setSuccess(true);
        return result;
    }
}
