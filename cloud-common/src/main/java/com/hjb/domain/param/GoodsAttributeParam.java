package com.hjb.domain.param;

import lombok.Data;

@Data
public class GoodsAttributeParam {

    private Long categoryId;

    private Long goodsId;

    private Long attrId;

    private String attrValue;

    private String goodsAttrImg;

    private Integer attrSort;
}
