package com.hjb.elastic.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Query {

    private String id;

    private String goodName;

    private String goodDesc;

    private String keyword;

    private Long brandId;

    private Long categoryId;

    private Long saleCount;

    private Integer isPublish;

    private Integer isNew;

    private Integer isRecommend;

    private Integer sort;

    private LocalDateTime createdTime;
    
}
