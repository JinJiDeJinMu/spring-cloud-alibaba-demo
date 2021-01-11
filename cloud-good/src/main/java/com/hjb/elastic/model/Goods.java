package com.hjb.elastic.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Goods {

    private Long id;

    private String goodsName;

    private String goodsDesc;

    private String mainImgUrl;

    private String keyword;

    private Long categoryId;

    private String categoryName;

    private Long brandId;

    private String brandName;

    private BigDecimal price;

    private Long saleCount;

    private Integer isPublish;

    private Integer isNew;

    private Integer isRecommend;

    private LocalDateTime createdTime;

    List<Attribute> goodsAttributes;

    @Data
    public static class Attribute{

        private Long id;

        private String name;

        private List<String> value;
    }
}
