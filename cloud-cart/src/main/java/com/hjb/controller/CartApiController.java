package com.hjb.controller;

import com.hjb.util.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 购物车接口
 * @author JinMu
 * @date 2020-12-09
 */
@RestController
@Api(tags = "CartApi")
@RequestMapping(value = "/api/cart")
public class CartApiController {


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result getCart(){

        return null;
    }

}
