package com.hjb.service.feign;

import com.hjb.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "service-good",fallback = GoodsFeignFallbackService.class)
public interface GoodsFeignService {


    @RequestMapping(value = "/good",method = RequestMethod.GET)
    Result get(@RequestParam(value = "id") Long id);

}
