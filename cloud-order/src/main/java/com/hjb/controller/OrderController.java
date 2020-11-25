package com.hjb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjb.domain.po.Order;
import com.hjb.feign.GoodsFeignService;
import com.hjb.service.OrderService;
import com.hjb.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * Order前端控制器
 * </p>
 *
 * @author jinmu
 * @since 2020-11-23
 */
@RestController
@Api(tags = "Order")
@RequestMapping("/order")
@RefreshScope
public class OrderController {

    @Autowired
    public OrderService orderService;

    @Autowired
    private GoodsFeignService goodsFeignService;

    /**
    * 根据主键id查询单条
    * @param id
    */
    @ApiOperation(value = "获取单条数据")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result getOrderById(Long id){
        return Result.SUCCESS(orderService.getById(id));
    }

    /**
    * 查询全部
    * @param param 查询条件
    */
    @ApiOperation(value = "全部查询", notes = "查询Order全部数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result getOrderAll(@RequestBody HashMap<String, Object> param){
        List<Order> result= orderService.listByMap(param);
        return Result.SUCCESS(result);
    }

    /**
    * 分页查询
    * @param param 查询条件
    */
    @ApiOperation(value = "分页查询", notes = "分页查询Order全部数据")
    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public Result getOrderPage(@RequestBody HashMap<String, Object> param){
        PageHelper.startPage(Integer.valueOf(param.get("pageNum").toString()), Integer.valueOf(param.get("pageSize").toString()));
        //手动构建查询条件
        List<Order> result= orderService.list();
        PageInfo pageInfo = new PageInfo(result);
        return Result.SUCCESS(pageInfo);
    }

    /**
    *
    * @param order
    */
    @ApiOperation(value = "订单提交")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result submit(@RequestBody Order order){
        return Result.SUCCESS(orderService.saveOrUpdate(order));
    }

}
