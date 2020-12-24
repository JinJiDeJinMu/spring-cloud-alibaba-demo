package com.hjb.api;

import com.hjb.domain.GoodsInfo;
import com.hjb.domain.dto.GoodsDetailDTO;
import com.hjb.service.GoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于feign远程调用
 */
@RestController
@RequestMapping(value = "/api/goods")
public class GoodsController {

    @Autowired
    private GoodsInfoService goodsInfoService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public GoodsInfo get(Long id){
        return goodsInfoService.getById(id);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public GoodsDetailDTO detail(Long id){
        return goodsInfoService.detail(id);
    }
}
