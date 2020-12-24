package com.hjb.api;

import com.hjb.domain.SkuInfo;
import com.hjb.service.SkuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/sku")
public class SkuController {

    @Autowired
    private SkuInfoService skuInfoService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public SkuInfo get(Long id){
        return skuInfoService.getById(id);
    }

    /**
     * 库存释放
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/addCount",method = RequestMethod.GET)
    public Boolean addSkuCount(Long skuId, Long  number){
        return skuInfoService.addSKUCount(skuId, number);
    }

    /**
     * 扣除库存
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/reduceCount",method = RequestMethod.GET)
    public Boolean reduceSkuCount(Long skuId, Long  number){
        return skuInfoService.reduceSKUCount(skuId, number);
    }
}
