package com.hjb.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDTO {

    private Long id;

    private Long goodId;

    private String orderSn;

    private Integer mount;

    private BigDecimal money;

    private LocalDateTime createTime;
}
