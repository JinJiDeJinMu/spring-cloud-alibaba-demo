package com.hjb.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemVo {

    private Long id;

    private Long skuId;

    private String goodsName;

    private String skuDesc;

    private String skuImg;

    private BigDecimal price;

    private Long number;

    private Integer isSelect;

}
