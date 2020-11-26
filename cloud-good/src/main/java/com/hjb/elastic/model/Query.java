package com.hjb.elastic.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Query {

    //查询内容
    private String content;

    private Long brandId;

    private Long categoryId;

    private Long saleCount;

    private BigDecimal price_min;

    private BigDecimal price_max;

    private Integer isPublish;

    private Integer isNew;

    private Integer isRecommend;

    private Integer sort;

    private LocalDateTime createdTime;

    private Integer pageSize;

    private Integer pageNum;
    
}
