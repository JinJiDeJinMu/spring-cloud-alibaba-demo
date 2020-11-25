package com.hjb.domain.param;

import lombok.Data;

import java.util.List;

@Data
public class GoodsInfoParam {

    private String goodsName;

    private String keyword;

    private String goodsDesc;

    private String goodsDetail;

    private String mainImgUrl;

    private String carouselImgUrl;

    private Long categoryId;

    private String categoryName;

    private Long brandId;

    private String brandName;

    private Integer isPublish;

    private Integer isNew;

    private Integer isRecommend;

    List<GoodsAttributeParam> goodsAttributeParamList;

    List<SkuInfoParam> skuInfoParamList;
}
