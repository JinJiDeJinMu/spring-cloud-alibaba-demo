package com.hjb.feign.fallback;

import com.hjb.domain.GoodsInfo;
import com.hjb.domain.SkuInfo;
import com.hjb.feign.GoodsFeignService;
import org.springframework.stereotype.Component;

@Component
public class GoodsFallback implements GoodsFeignService {
    @Override
    public SkuInfo getSkuInfoById(Long id) {
        return null;
    }

    @Override
    public GoodsInfo goods(Long id) {
        return null;
    }
}
