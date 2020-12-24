package com.hjb.feign;

import com.hjb.domain.GoodsInfo;
import com.hjb.domain.SkuInfo;
import com.hjb.domain.dto.GoodsDetailDTO;
import com.hjb.feign.fallback.GoodsFeignFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "service-good",fallback = GoodsFeignFallbackService.class)
public interface GoodsFeignService {


    @RequestMapping(value = "/api/goods/detail",method = RequestMethod.GET)
    GoodsDetailDTO goodsDetail(@RequestParam(value = "id") Long id);

    @RequestMapping(value = "/api/goods/get",method = RequestMethod.GET)
    GoodsInfo goods(@RequestParam(value = "id") Long id);

    @RequestMapping(value = "/api/sku/get", method = RequestMethod.GET)
    SkuInfo getSkuInfoById(@RequestParam(value = "id") Long id);

    @RequestMapping(value = "/api/sku/addCount",method = RequestMethod.GET)
    Boolean addSkuCount(@RequestParam(value = "skuId") Long skuId, @RequestParam(value = "number") Long number);

    @RequestMapping(value = "/api/sku/reduceCount",method = RequestMethod.GET)
    Boolean reduceSkuCount(@RequestParam(value = "skuId") Long skuId, @RequestParam(value = "number") Long number);

}
