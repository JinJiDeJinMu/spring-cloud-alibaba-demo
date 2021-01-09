package com.hjb.controller;

import com.hjb.service.WeixinPayService;
import com.hjb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/wxpay")
public class WeinxinPayController {

    @Autowired
    private WeixinPayService weixinPayService;

    @PostMapping("/create")
    public Result created(String out_order_sn, String total_price){
        return Result.SUCCESS(weixinPayService.createNative(out_order_sn, total_price));
    }
}
