package com.hjb.mq.processor;

import com.hjb.mq.common.MQConsumeResult;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/***
 * 所有消息处理继承该类
 */
public abstract class AbstractMQMsgProcessor implements MQMsgProcessor {


    @Override
    public MQConsumeResult handle(String tag, List<MessageExt> msgs) {
        MQConsumeResult mqConsumeResult = new MQConsumeResult();
        /**可以增加一些其他逻辑*/
        for (MessageExt messageExt : msgs) {
            mqConsumeResult = this.consumeMessage(tag, messageExt);
        }
        /**可以增加一些其他逻辑*/
        return mqConsumeResult;
    }

    /***
     * 消息某条消息
     * @param tag
     * @param messageExt
     * @return
     */
    protected abstract MQConsumeResult consumeMessage(String tag, MessageExt messageExt);
}
