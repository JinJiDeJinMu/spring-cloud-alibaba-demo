package com.hjb.feign;

import com.hjb.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class GoodsFeignFallbackService implements GoodsFeignService {
    @Override
    public Result get(Long id) {
        log.info("服务容错接口启动》》》》》》");
        GoodsDTO goodsDTO = new GoodsDTO();
        goodsDTO.setId(1l);
        goodsDTO.setName("异常商品");
        goodsDTO.setMoney(new BigDecimal(100));
        goodsDTO.setGoodsSn("asads123");
        return Result.SUCCESS(goodsDTO);
    }
}
