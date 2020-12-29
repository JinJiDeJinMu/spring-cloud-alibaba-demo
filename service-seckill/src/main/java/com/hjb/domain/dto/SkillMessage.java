package com.hjb.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SkillMessage implements Serializable {

    private static final long serialVersionUID=1L;

    private Long orderSn;

    private Long skuId;

    private Long userId;

    private Integer num;

    private BigDecimal SkillPrice;
}
