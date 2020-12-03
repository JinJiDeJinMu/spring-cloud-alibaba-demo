package com.hjb.feign;


import com.hjb.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-user", fallback =UserFallBackServiceImpl.class)
public interface UserFeignService {

    @RequestMapping(value = "/user/get", method = RequestMethod.GET)
    Result getUserById(@RequestParam("id") Long id);
}
