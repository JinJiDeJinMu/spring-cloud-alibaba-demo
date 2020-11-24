package com.hjb.feign;

import com.hjb.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "service-good",fallback = GoodsFeignFallbackService.class)
public interface GoodsFeignService {


    @RequestMapping(value = "goodsInfo/detail",method = RequestMethod.GET)
    Result goodsDetail(@RequestParam(value = "id") Long id);

    @RequestMapping(value = "skuInfo/get", method = RequestMethod.GET)
    Result getSkuInfoById(@RequestParam(value = "id") Long id);
}
