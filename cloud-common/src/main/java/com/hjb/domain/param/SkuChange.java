package com.hjb.domain.param;

import com.hjb.domain.SkuInfo;
import lombok.Data;

@Data
public class SkuChange {

    private SkuInfo skuInfo;

    private long number;
}
