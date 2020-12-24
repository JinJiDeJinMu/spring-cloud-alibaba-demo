package com.hjb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjb.domain.OrderItem;
import com.hjb.service.OrderItemService;
import com.hjb.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * OrderItem前端控制器
 * </p>
 *
 * @author jinmu
 * @since 2020-11-23
 */
@RestController
@Api(tags = "OrderItem")
@RequestMapping("/orderItem")
public class OrderItemController {

    @Autowired
    public OrderItemService orderItemService;

    /**
    * 根据主键id查询单条
    * @param id
    */
    @ApiOperation(value = "获取单条数据")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result getOrderItemById(Long id){
        return Result.SUCCESS(orderItemService.getById(id));
    }

    /**
    * 查询全部
    * @param param 查询条件
    */
    @ApiOperation(value = "全部查询", notes = "查询OrderItem全部数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result getOrderItemAll(@RequestBody HashMap<String, Object> param){
        List<OrderItem> result= orderItemService.listByMap(param);
        return Result.SUCCESS(result);
    }

    /**
    * 分页查询
    * @param param 查询条件
    */
    @ApiOperation(value = "分页查询", notes = "分页查询OrderItem全部数据")
    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public Result getOrderItemPage(@RequestBody HashMap<String, Object> param){
        PageHelper.startPage(Integer.valueOf(param.get("pageNum").toString()), Integer.valueOf(param.get("pageSize").toString()));
        //手动构建查询条件
        List<OrderItem> result= orderItemService.list();
        PageInfo pageInfo = new PageInfo(result);
        return Result.SUCCESS(pageInfo);
    }

    /**
    * 更新保存单条数据
    * @param orderItem
    */
    @ApiOperation(value = "更新保存单条数据")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result addOrUpdateOrderItem(@RequestBody OrderItem orderItem){
        return Result.SUCCESS(orderItemService.saveOrUpdate(orderItem));
    }

    /**
    * 批量删除
    * @param ids
    */
    @ApiOperation(value = "批量删除数据")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result deleteOrderItemById(List<Long> ids){
        return Result.SUCCESS(orderItemService.removeByIds(ids));
    }

}
