package com.hjb.elastic.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class EsGoods {

    private Long id;

    private String goodName;

    private String goodDesc;

    private String imgUrl;

    private Long catalogId;

    private Long brandId;

    private Integer publishStatus;

    private Integer isNew;

    private BigDecimal price;

    private Long mount;

    private Long saleCount;

    private LocalDateTime createdTime;
}
