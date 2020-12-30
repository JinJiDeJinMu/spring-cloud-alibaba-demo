package com.hjb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjb.domain.po.OrderReturnReason;
import com.hjb.service.OrderReturnReasonService;
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
 * OrderReturnReasoncontroller
 * </p>
 *
 * @author jinmu
 * @date 2020-12-30
 */
@RestController
@Api(tags = "OrderReturnReason")
@RequestMapping("/orderReturnReason")
public class OrderReturnReasonController {

    @Autowired
    public OrderReturnReasonService orderReturnReasonService;

    /**
    * 根据主键id查询单条
    * @param id
    */
    @ApiOperation(value = "获取单条数据")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result getOrderReturnReasonById(Long id){
        return Result.SUCCESS(orderReturnReasonService.getById(id));
    }

    /**
    * 查询全部
    * @param param 查询条件
    */
    @ApiOperation(value = "全部查询", notes = "查询OrderReturnReason全部数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result getOrderReturnReasonAll(@RequestBody HashMap<String, Object> param){
        List<OrderReturnReason> result= orderReturnReasonService.listByMap(param);
        return Result.SUCCESS(result);
    }

    /**
    * 分页查询
    * @param param 查询条件
    */
    @ApiOperation(value = "分页查询", notes = "分页查询OrderReturnReason全部数据")
    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public Result getOrderReturnReasonPage(@RequestBody HashMap<String, Object> param){
        PageHelper.startPage(Integer.valueOf(param.get("pageNum").toString()), Integer.valueOf(param.get("pageSize").toString()));
        //手动构建查询条件
        List<OrderReturnReason> result= orderReturnReasonService.list();
        PageInfo pageInfo = new PageInfo(result);
        return Result.SUCCESS(pageInfo);
    }

    /**
    * 更新保存单条数据
    * @param orderReturnReason
    */
    @ApiOperation(value = "更新保存单条数据")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result addOrUpdateOrderReturnReason(@RequestBody OrderReturnReason orderReturnReason){
        return Result.SUCCESS(orderReturnReasonService.saveOrUpdate(orderReturnReason));
    }

    /**
    * 批量删除
    * @param ids
    */
    @ApiOperation(value = "批量删除数据")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result deleteOrderReturnReasonById(List<Long> ids){
        return Result.SUCCESS(orderReturnReasonService.removeByIds(ids));
    }

}
