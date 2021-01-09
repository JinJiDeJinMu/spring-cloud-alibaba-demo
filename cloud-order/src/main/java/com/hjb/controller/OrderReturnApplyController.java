package com.hjb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjb.domain.param.OrderReturnParam;
import com.hjb.domain.po.OrderReturnApply;
import com.hjb.service.OrderReturnApplyService;
import com.hjb.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * OrderReturnApplycontroller
 * </p>
 *
 * @author jinmu
 * @date 2020-12-30
 */
@RestController
@Api(tags = "OrderReturnApply")
@RequestMapping("/orderReturnApply")
public class OrderReturnApplyController {

    @Autowired
    public OrderReturnApplyService orderReturnApplyService;

    /**
    * 根据主键id查询单条
    * @param id
    */
    @ApiOperation(value = "获取单条数据")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result getOrderReturnApplyById(Long id){
        return Result.SUCCESS(orderReturnApplyService.getById(id));
    }

    /**
    * 查询全部
    * @param param 查询条件
    */
    @ApiOperation(value = "全部查询", notes = "查询OrderReturnApply全部数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result getOrderReturnApplyAll(@RequestBody HashMap<String, Object> param){
        List<OrderReturnApply> result= orderReturnApplyService.listByMap(param);
        return Result.SUCCESS(result);
    }

    /**
    * 分页查询
    * @param param 查询条件
    */
    @ApiOperation(value = "分页查询", notes = "分页查询OrderReturnApply全部数据")
    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public Result getOrderReturnApplyPage(@RequestBody HashMap<String, Object> param){
        PageHelper.startPage(Integer.valueOf(param.get("pageNum").toString()), Integer.valueOf(param.get("pageSize").toString()));
        //手动构建查询条件
        List<OrderReturnApply> result= orderReturnApplyService.list();
        PageInfo pageInfo = new PageInfo(result);
        return Result.SUCCESS(pageInfo);
    }

    /**
    * 更新保存单条数据
    * @param orderReturnApply
    */
    @ApiOperation(value = "更新保存单条数据")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result addOrUpdateOrderReturnApply(@RequestBody OrderReturnApply orderReturnApply){
        return Result.SUCCESS(orderReturnApplyService.saveOrUpdate(orderReturnApply));
    }

    /**
    * 批量删除
    * @param ids
    */
    @ApiOperation(value = "批量删除数据")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result deleteOrderReturnApplyById(List<Long> ids){
        return Result.SUCCESS(orderReturnApplyService.removeByIds(ids));
    }

    /**
     *订单售后
     * @param orderReturnParam
     * @return
     */
    @ApiOperation(value = "订单售后")
    @RequestMapping(value = "/return", method = RequestMethod.POST)
    public Result orderReturn(@RequestBody @Validated OrderReturnParam orderReturnParam){
        return Result.SUCCESS(orderReturnApplyService.createOrderReturn(orderReturnParam));
    }

    /**
     * 售后订单发货
     * @return
     */
    @ApiOperation(value = "订单售后")
    @RequestMapping(value = "/express", method = RequestMethod.GET)
    public Result orderExpress(Long id, String expressCode){
        return Result.SUCCESS(orderReturnApplyService.orderExpress(id,expressCode));
    }

    /**
     * 更新售后订单状态
     * @param id
     * @param status
     * @return
     */
    @ApiOperation(value = "订单售后")
    @RequestMapping(value = "/express", method = RequestMethod.GET)
    public Result updateOrderReturn(Long id, Integer status){
        return Result.SUCCESS(orderReturnApplyService.updateOrderReturn(id,status));
    }

}
