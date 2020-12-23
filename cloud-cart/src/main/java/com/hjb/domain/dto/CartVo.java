package com.hjb.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartVo {

    private Long brandId;

    private String brandName;

    private List<CartItemVo> cartItemVos;

}
