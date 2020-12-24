package com.hjb.feign;

import com.hjb.domain.GoodsInfo;
import com.hjb.domain.SkuInfo;
import com.hjb.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-good",fallback = GoodsFeignService.class)
public interface GoodsFeignService {

    @RequestMapping(value = "/api/sku/get", method = RequestMethod.GET)
    SkuInfo getSkuInfoById(@RequestParam(value = "id") Long id);

    @RequestMapping(value = "/api/goods/get",method = RequestMethod.GET)
    GoodsInfo goods(@RequestParam(value = "id") Long id);
}
