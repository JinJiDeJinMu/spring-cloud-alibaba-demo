package com.hjb.domain.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GoodsDetailDTO {

    private Long id;

    private String goodName;

    private String goodDesc;

    private Long catalogId;

    private Long brandId;

    private Integer publishStatus;

    private Integer isNew;

    private Date createdTime;

    private Date updateTime;

    private List<SkuInfoDTO> skuInfoDTOS;
}
