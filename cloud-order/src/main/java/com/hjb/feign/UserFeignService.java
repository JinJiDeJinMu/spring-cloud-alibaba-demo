package com.hjb.feign;

import com.hjb.feign.fallback.UserFeignFallbackService;
import com.hjb.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-user",fallback = UserFeignFallbackService.class)
public interface UserFeignService {

    /**
     * 查询用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/get", method = RequestMethod.GET)
    Result getUserById(@RequestParam("id") Long id);

    @RequestMapping(value = "/receiveAddress/userAddress", method = RequestMethod.GET)
    Result getReceiveAddressByUserId(@RequestParam("userId") Long userId);

    /**
     * 查询收货地址详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/receiveAddress/get", method = RequestMethod.GET)
    Result getReceiveAddressById(@RequestParam("id")Long id);
}
