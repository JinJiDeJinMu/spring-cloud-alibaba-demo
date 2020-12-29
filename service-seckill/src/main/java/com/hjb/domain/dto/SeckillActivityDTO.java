package com.hjb.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SeckillActivityDTO {

    private Long activityId;

    private String title;

    private Date startTime;

    private Date endTime;

    private Long skuId;

    private BigDecimal skillPrice;

    private Long skillLimitCount;

}
