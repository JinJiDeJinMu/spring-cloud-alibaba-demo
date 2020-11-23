package com.hjb.elastic.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EsGoodsSKU {

    private Long id;

    private Long goodsId;

    private String goodName;

    private String goodDesc;

    private String imgUrl;

    private Long catalogId;

    private Long brandId;

    private Integer publishStatus;

    private Integer isNew;

    private String attrList;

    private String skuImg;

    private String skuDesc;

    private String skuTitle;

    private BigDecimal price;

    private Long mount;

    private Long saleCount;

    private LocalDateTime createdTime;
}
