package com.hjb.constant;

public enum MQTopicConstants {
    ORDER_CANCEL_TOPIC("order-cancel","*"),
    ORDER_KILL_TOPIC("order-kill","*")
    ;

    private String topic;

    private String tags;

    public String getTopic() {
        return topic;
    }
    public String getTags() {
        return tags;
    }
    private MQTopicConstants(String topic, String tags){
        this.tags = tags;
        this.topic = topic;
    }

    public static MQTopicConstants getEnumByKey(String topic) {
        if (null == topic) {
            return null;
        }
        for (MQTopicConstants temp : MQTopicConstants.values()) {
            if (temp.getTopic().equals(topic)) {
                return temp;
            }
        }
        return null;
    }
}
