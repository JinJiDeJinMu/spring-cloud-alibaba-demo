package com.hjb.feign.fallback;

import com.hjb.domain.GoodsInfo;
import com.hjb.domain.SkuInfo;
import com.hjb.domain.dto.GoodsDetailDTO;
import com.hjb.feign.GoodsFeignService;
import com.hjb.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class GoodsFeignFallbackService implements GoodsFeignService {


    @Override
    public GoodsDetailDTO goodsDetail(Long id) {
        return null;
    }

    @Override
    public GoodsInfo goods(Long id) {
        return null;
    }

    @Override
    public SkuInfo getSkuInfoById(Long id) {
        return null;
    }

    @Override
    public Boolean addSkuCount(Long skuId, Long number) {
        return null;
    }

    @Override
    public Boolean reduceSkuCount(Long skuId, Long number) {
        return null;
    }

}
