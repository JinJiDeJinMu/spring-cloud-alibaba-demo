package com.hjb.domain.param;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderParam {

    private Long userId;

    private Long goodsId;

    private Long couponId;

    private Long skuId;

    private Long number;

    private Long addressId;

    private Integer buyType;

    private BigDecimal price;
}
