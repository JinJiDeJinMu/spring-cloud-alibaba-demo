package com.hjb.service;

import com.hjb.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "service-good",fallback = GoodsFeignFallbackService.class)
public interface GoodsFeignService {


    @RequestMapping(value = "/api/v1/service-good/good",method = RequestMethod.GET)
    Result get(@RequestParam(value = "id") Long id);

}
