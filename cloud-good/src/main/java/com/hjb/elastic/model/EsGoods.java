package com.hjb.elastic.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EsGoods {

    private Long id;

    private String goodName;

    private String goodDesc;

    private String mainImgUrl;

    private Long categoryId;

    private Long brandId;

    private BigDecimal price;

    private Long saleCount;

    private Integer isPublish;

    private Integer isNew;

    private Integer isRecommend;

    private LocalDateTime createdTime;
}
