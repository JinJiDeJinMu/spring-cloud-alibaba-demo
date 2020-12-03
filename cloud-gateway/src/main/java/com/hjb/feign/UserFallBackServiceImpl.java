package com.hjb.feign;

import com.hjb.util.Result;
import org.springframework.stereotype.Component;

@Component
public class UserFallBackServiceImpl implements UserFeignService{
    @Override
    public Result getUserById(Long id) {
        return null;
    }
}
