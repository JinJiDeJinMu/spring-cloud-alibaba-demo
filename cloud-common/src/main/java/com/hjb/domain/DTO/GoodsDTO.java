package com.hjb.domain.DTO;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class GoodsDTO {

    private Long id;

    private String goodSn;

    private String goodName;

    private BigDecimal money;
}
