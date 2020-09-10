package com.hjb.service;

import com.hjb.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-good")
public interface GoodsFeignService {

    @GetMapping(value = "/api/v1/service-good/good/{id}")
    Result get(@PathVariable("id") Long id);
}
