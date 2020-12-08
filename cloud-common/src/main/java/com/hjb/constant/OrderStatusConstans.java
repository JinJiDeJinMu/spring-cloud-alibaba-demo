package com.hjb.constant;

public enum  OrderStatusConstans {

    WAIT_PAY(0, "待付款"),
    CANCEL_ORDER(101, "已取消"),
    CLOSED_ORDER(102, "已关闭"),

    WAIT_SEND_GOODS(201, "待发货"),
    SHIPPED_ORDER(202, "已发货"),




    CONFIRM_GOODS(301, "确认收货"),
    REFUND_ORDER(401, "已退款"),
    COMPLETED_ORDER(402, "已完成"),
    PINGLUN_ORDER(403, "已评价"),

    APPLY_REFUND_GOODS(501, "申请退货"),
    GOOD_SENDING(502, "退货寄回中"),
    STORM_GET_GOODS(503, "仓库已收退货"),
    STORM_REJECT_GOODS(504, "仓库拒绝退货");


    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }

    private String desc;
    private Integer code;

    private OrderStatusConstans(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OrderStatusConstans getEnumByKey(Integer key) {
        if (null == key) {
            return null;
        }
        for (OrderStatusConstans temp : OrderStatusConstans.values()) {
            if (temp.getCode().equals(key)) {
                return temp;
            }
        }
        return null;
    }
}
