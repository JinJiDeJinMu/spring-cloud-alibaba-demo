package com.hjb.constant;

public enum  OrderStatusConstans {

    WAIT_PAY(0, "待付款"),
    CANCEL_ORDER(101, "已取消"),
    CLOSED_ORDER(102, "已关闭"),

    WAIT_SEND_GOODS(201, "待发货"),
    SHIPPED_ORDER(202, "已发货"),


    CONFIRM_GOODS(301, "确认收货"),

    COMPLETED_ORDER(400, "已完成"),
    PINGJIA_ORDER(401, "已评价"),

    APPLY_RETURN_MONEY(500,"申请退款"),
    APPLY_REFUND_GOODS(501, "申请退货"),
    APPLY_RETURN_MONEY_GOODS(502,"申请退款退货"),
    GOOD_SENDING(503, "退货寄回中"),
    ORDER_RETURN_MONEY(504,"已退款"),
    ORDER_RETURN_GOODS(505,"已换货"),
    STORM_GET_GOODS(506, "仓库已收退货"),
    STORM_REJECT_GOODS(507, "仓库拒绝退货");


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
