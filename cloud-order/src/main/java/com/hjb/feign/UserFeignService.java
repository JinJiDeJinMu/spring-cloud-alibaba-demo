package com.hjb.feign;

import com.hjb.domain.ReceiveAddress;
import com.hjb.domain.User;
import com.hjb.feign.fallback.UserFeignFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "service-user",fallback = UserFeignFallbackService.class)
public interface UserFeignService {

    /**
     * 查询用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/user/get", method = RequestMethod.GET)
    User getUserById(@RequestParam("id") Long id);


    @RequestMapping(value = "/api/address/listByUserId", method = RequestMethod.GET)
    List<ReceiveAddress> getReceiveAddressByUserId(@RequestParam("userId") Long userId);

    /**
     * 查询收货地址详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/address/get", method = RequestMethod.GET)
    ReceiveAddress getReceiveAddressById(@RequestParam("id") Long id);
}
