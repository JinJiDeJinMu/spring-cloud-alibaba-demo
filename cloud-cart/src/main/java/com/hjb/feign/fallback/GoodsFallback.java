package com.hjb.feign.fallback;

import com.hjb.feign.GoodsFeignService;
import com.hjb.util.Result;
import org.springframework.stereotype.Component;

@Component
public class GoodsFallback implements GoodsFeignService {
    @Override
    public Result getSkuInfoById(Long id) {
        return Result.FAILURE("服务调用出错");
    }

    @Override
    public Result goods(Long id) {
        return Result.FAILURE("服务调用出错");
    }
}
