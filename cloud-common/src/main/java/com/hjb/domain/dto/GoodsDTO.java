package com.hjb.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class GoodsDTO {

    private Long id;

    private String goodsSn;

    private String name;

    private BigDecimal money;

    private LocalDateTime createTime;
}
