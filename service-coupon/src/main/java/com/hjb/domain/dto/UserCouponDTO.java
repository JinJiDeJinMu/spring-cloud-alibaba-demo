package com.hjb.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserCouponDTO {

    private Long couponId;

    private String couponName;

    private String couponImg;

    private Integer couponType;

    private BigDecimal pointAmount;

    private BigDecimal amount;

    private Integer useType;

    private Integer timeDay;

    private Integer timeType;

    private Date startTime;

    private Date endTime;

    private String remark;

    private String code;

    private Integer status;
}
