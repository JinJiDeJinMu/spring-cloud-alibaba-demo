package com.hjb.domain.dto;

import lombok.Data;

@Data
public class SkuAttrDTO {

    private Long id;

    private Long skuId;

    private Long attrId;

    private String attrName;

    private String attrValue;

    private Integer attrSort;

    private Integer isDeleted;
}
