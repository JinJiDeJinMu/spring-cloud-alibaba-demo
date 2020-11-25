package com.hjb.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GoodsDetailDTO {

    private Long id;

    private String goodsName;

    private String keyword;

    private String goodsDesc;

    private String goodsDetail;

    private String mainImgUrl;

    private String carouselImgUrl;

    private String categoryName;

    private String brandName;

    private Integer isPublish;

    private Integer isNew;

    private Integer isRecommend;

    private LocalDateTime createdTime;

    private LocalDateTime updateTime;

    private List<String> attrs;

    private List<SkuInfoDTO> skuInfoDTOS;
}
