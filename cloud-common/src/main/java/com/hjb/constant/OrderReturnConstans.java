package com.hjb.constant;

public enum OrderReturnConstans {
    RETURN_MONEY(0,500),
    RETURN_GOODS(1,501),
    RETURN_MONEY_GOODS(2,502)
    ;

    private Integer type;

    private Integer status;

    OrderReturnConstans(Integer type, Integer status) {
        this.type = type;
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static Integer getStatusByType(Integer type) {
        if (null == type) {
            return null;
        }
        for (OrderReturnConstans temp : OrderReturnConstans.values()) {
            if (temp.getType().equals(type)) {
                return temp.status;
            }
        }
        return null;
    }
}
