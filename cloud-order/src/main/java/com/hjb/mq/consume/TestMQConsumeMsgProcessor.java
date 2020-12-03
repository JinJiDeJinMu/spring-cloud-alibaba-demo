package com.hjb.mq.consume;

import com.hjb.mq.annotation.MQConsumeService;
import com.hjb.mq.common.MQConsumeResult;
import com.hjb.mq.processor.AbstractMQMsgProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;

@Slf4j
@MQConsumeService(topic = "topic1", tags = "*")
public class TestMQConsumeMsgProcessor extends AbstractMQMsgProcessor {

    @Override
    protected MQConsumeResult consumeMessage(String tag, MessageExt messageExt) {
        log.info("消息ID: {}, 消息体: {}",messageExt.getMsgId(), new String(messageExt.getBody()));
        //TODO 判断该消息是否重复消费（RocketMQ不保证消息不重复，如果你的业务需要保证严格的不重复消息，需要你自己在业务端去重）

        //如果注解中tags数据中包含多个tag或者是全部的tag(*)，则需要根据tag判断是那个业务，
        //如果注解中tags为具体的某个tag，则该服务就是单独针对tag处理的
        if (tag.equals("tag2")) {
            //做某个操作
        }
        //TODO 获取该消息重试次数
        int reconsume = messageExt.getReconsumeTimes();
        //根据消息重试次数判断是否需要继续消费
        if (reconsume == 3) {
            //消息已经重试了3次，如果不需要再次消费，则返回成功
        }
        MQConsumeResult result = new MQConsumeResult();
        result.setSuccess(true);
        return result;
    }

}
