package com.hjb.domain.param;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SkuInfoParam {

    private Long goodsId;

    private String attrList;

    private String skuImg;

    private String skuDesc;

    private String skuTitle;

    private BigDecimal price;

    private Long mount;

}
