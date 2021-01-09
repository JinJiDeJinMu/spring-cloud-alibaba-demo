package com.hjb.domain.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class OrderReturnParam {

    @NotNull(message = "订单Id不能为空")
    private Long orderId;

    @NotNull(message = "订单号不能为空")
    private String orderSn;

    @NotNull(message = "售后类型不能为空")
    private Integer type;

    private BigDecimal returnMoney;

    @NotNull(message = "售后原因不能为空")
    private String returnReason;

    private String description;

    private String descPics;

    private String returnAddress;

    private String returnName;

    private String returnPhone;

}
