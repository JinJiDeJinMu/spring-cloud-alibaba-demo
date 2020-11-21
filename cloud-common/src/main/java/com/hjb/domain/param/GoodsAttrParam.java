package com.hjb.domain.param;

import lombok.Data;

@Data
public class GoodsAttrParam {

    private Long goodsId;

    private Long attrId;

    private String attrName;

    private String attrValue;

    private String goodsAttrImg;

    private Integer attrSort;
}
