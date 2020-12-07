package com.hjb.feign.fallback;

import com.hjb.feign.UserFeignService;
import com.hjb.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserFeignFallbackService implements UserFeignService {
    @Override
    public Result getUserById(Long id) {
        return Result.FAILURE("用户服务调用出错");
    }

    @Override
    public Result getReceiveAddressByUserId(Long userId) {
        return Result.FAILURE("用户服务调用出错");
    }

    @Override
    public Result getReceiveAddressById(Long id) {
        return Result.FAILURE("用户服务调用出错");
    }
}
