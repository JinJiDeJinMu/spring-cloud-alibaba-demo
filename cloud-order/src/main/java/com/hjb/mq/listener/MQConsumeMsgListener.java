package com.hjb.mq.listener;

import com.alibaba.fastjson.JSON;
import com.hjb.mq.annotation.MQConsumeService;
import com.hjb.mq.common.MQConsumeResult;
import com.hjb.mq.exception.AppException;
import com.hjb.mq.exception.RocketMQErrorEnum;
import com.hjb.mq.exception.RocketMQException;
import com.hjb.mq.processor.MQMsgProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MQConsumeMsgListener implements MessageListenerConcurrently {


    @Autowired
    private Map<String, MQMsgProcessor> mqMsgProcessorServiceMap;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        if (CollectionUtils.isEmpty(msgs)) {
            log.info("接受到的消息为空，不处理，直接返回成功");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        ConsumeConcurrentlyStatus concurrentlyStatus = ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        try {
            //根据Topic分组
            Map<String, List<MessageExt>> topicGroups = msgs.stream().collect(Collectors.groupingBy(MessageExt::getTopic));
            for (Map.Entry<String, List<MessageExt>> topicEntry : topicGroups.entrySet()) {
                String topic = topicEntry.getKey();
                //根据tags分组
                Map<String, List<MessageExt>> tagGroups = topicEntry.getValue().stream().collect(Collectors.groupingBy(MessageExt::getTags));
                for (Map.Entry<String, List<MessageExt>> tagEntry : tagGroups.entrySet()) {
                    String tag = tagEntry.getKey();
                    //消费某个主题下，tag的消息
                    this.consumeMsgForTag(topic, tag, tagEntry.getValue());
                }
            }
        } catch (Exception e) {
            log.error("处理消息失败", e);
            concurrentlyStatus = ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        // 如果没有return success ，consumer会重新消费该消息，直到return success
        return concurrentlyStatus;

    }

    /***
     * 根据topic 和 tags路由，查找消费消息服务
     * @param topic
     * @param tag
     * @param value
     */
    private void consumeMsgForTag(String topic, String tag, List<MessageExt> value) {
        //根据topic 和  tag查询具体的消费服务
        MQMsgProcessor imqMsgProcessor = selectConsumeService(topic, tag);
        try {
            if (imqMsgProcessor == null) {
                log.error("根据Topic：{}和Tag: {} 没有找到对应的处理消息的服务", topic, tag);
                throw new RocketMQException(RocketMQErrorEnum.NOT_FOUND_CONSUMESERVICE);
            }
            log.info("根据Topic：{}和Tag: {} ,消息大小: {} ,路由到的服务为: {} ,开始调用处理消息", topic, tag, value.size(), imqMsgProcessor.getClass().getName());
            //调用该类的方法,处理消息
            MQConsumeResult mqConsumeResult = imqMsgProcessor.handle(tag, value);
            if (mqConsumeResult == null) {
                throw new RocketMQException(RocketMQErrorEnum.HANDLE_RESULT_NULL);
            }
            if (mqConsumeResult.isSuccess()) {
                log.info("消息处理成功：" + JSON.toJSONString(mqConsumeResult));
            } else {
                throw new RocketMQException(RocketMQErrorEnum.CONSUME_FAIL, JSON.toJSONString(mqConsumeResult), false);
            }
            if (mqConsumeResult.isSaveConsumeLog()) {
                log.debug("开始记录消费日志");
                //TODO 记录消费日志
            }
        } catch (Exception e) {
            if (e instanceof AppException) {
                AppException mqe = (AppException) e;
                //TODO 记录消费失败日志
                throw new AppException(mqe.getErrCode(), mqe.getErrMsg(), false);
            } else {
                //TODO 记录消费失败日志
                throw e;
            }
        }
    }

    /***
     * 根据topic和tag查询对应的具体的消费服务
     * @param topic
     * @param tag
     * @return
     */
    private MQMsgProcessor selectConsumeService(String topic, String tag) {
        MQMsgProcessor imqMsgProcessor = null;
        for (Map.Entry<String, MQMsgProcessor> entry : mqMsgProcessorServiceMap.entrySet()) {
            //获取service实现类上注解的topic和tags
            MQConsumeService consumeService = entry.getValue().getClass().getAnnotation(MQConsumeService.class);
            if (consumeService == null) {
                log.error("消费者服务：" + entry.getValue().getClass().getName() + "上没有添加MQConsumeService注解");
                continue;
            }
            String annotationTopic = consumeService.topic();
            if (!annotationTopic.equals(topic)) {
                continue;
            }
            String[] tagsArr = consumeService.tags();
            //"*"号表示订阅该主题下所有的tag
            if (tagsArr[0].equals("*")) {
                //获取该实例
                imqMsgProcessor = entry.getValue();
                break;
            }
            boolean isContains = Arrays.asList(tagsArr).contains(tag);
            if (isContains) {
                //获取该实例
                imqMsgProcessor = entry.getValue();
                break;
            }
        }
        return imqMsgProcessor;
    }
}
