package com.hjb.feign.fallback;

import com.hjb.domain.ReceiveAddress;
import com.hjb.domain.User;
import com.hjb.feign.UserFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class UserFeignFallbackService implements UserFeignService {
    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public List<ReceiveAddress> getReceiveAddressByUserId(Long userId) {
        return null;
    }

    @Override
    public ReceiveAddress getReceiveAddressById(Long id) {
        return null;
    }
}
