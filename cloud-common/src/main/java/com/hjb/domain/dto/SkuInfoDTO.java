package com.hjb.domain.dto;

import com.hjb.domain.po.SkuAttr;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuInfoDTO {

    private Long id;

    private Long goodsId;

    private String skuImg;

    private String skuDesc;

    private String skuTitle;

    private BigDecimal price;

    private Long mount;

    private Long saleCount;

    private List<SkuAttr> skuAttr;
}
