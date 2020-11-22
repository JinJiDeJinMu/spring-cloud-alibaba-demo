package com.hjb.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class GoodsDetailDTO {

    private Long id;

    private String goodName;

    private String goodDesc;

    private String imgUrl;

    private Long catalogId;

    private Long brandId;

    private Integer publishStatus;

    private Integer isNew;

    private LocalDateTime createdTime;

    private LocalDateTime updateTime;

    private List<SkuInfoDTO> skuInfoDTOS;
}
