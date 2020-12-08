package com.hjb.feign.fallback;

import com.hjb.domain.param.SkuChange;
import com.hjb.domain.po.SkuInfo;
import com.hjb.feign.GoodsFeignService;
import com.hjb.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class GoodsFeignFallbackService implements GoodsFeignService {


    @Override
    public Result goodsDetail(Long id) {
        return Result.FAILURE("服务调用出错");
    }

    @Override
    public Result goods(Long id) {
        return Result.FAILURE("服务调用出错");
    }

    @Override
    public Result getSkuInfoById(Long id) {
        return Result.FAILURE("服务调用出错");
    }

    @Override
    public Result addSkuCount(Long skuId, Long number) {
        return Result.FAILURE("服务调用出错");
    }

    @Override
    public Result reduceSkuCount(Long skuId, Long number) {
        return Result.FAILURE("服务调用出错");
    }

}
