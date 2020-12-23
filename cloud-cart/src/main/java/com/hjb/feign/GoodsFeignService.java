package com.hjb.feign;

import com.hjb.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-good",fallback = GoodsFeignService.class)
public interface GoodsFeignService {

    @RequestMapping(value = "skuInfo/get", method = RequestMethod.GET)
    Result getSkuInfoById(@RequestParam(value = "id") Long id);

    @RequestMapping(value = "goodsInfo/get",method = RequestMethod.GET)
    Result goods(@RequestParam(value = "id") Long id);
}
