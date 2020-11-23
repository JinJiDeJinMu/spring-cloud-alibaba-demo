package com.hjb.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SkuInfoDTO {

    private Long id;

    private Long goodsId;

    private String skuImg;

    private String skuDesc;

    private String skuTitle;

    private BigDecimal price;

    private Long mount;

    private Long saleCount;

}
