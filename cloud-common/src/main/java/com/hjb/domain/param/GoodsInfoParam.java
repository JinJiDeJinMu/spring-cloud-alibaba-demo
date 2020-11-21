package com.hjb.domain.param;

import lombok.Data;

import java.util.List;

@Data
public class GoodsInfoParam {

    private String goodName;

    private String goodDesc;

    private Long catalogId;

    private Long brandId;

    private Integer publishStatus;

    private Integer isNew;

    private List<GoodsAttrParam> goodsAttrParams;
}
