package com.hjb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjb.domain.ReceiveAddress;
import com.hjb.service.ReceiveAddressService;
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
 * ReceiveAddresscontroller
 * </p>
 *
 * @author jinmu
 * @date 2020-11-30
 */
@RestController
@Api(tags = "ReceiveAddress")
@RequestMapping("/receiveAddress")
public class ReceiveAddressController {

    @Autowired
    public ReceiveAddressService receiveAddressService;

    /**
    * 查询用户地址列表
    * @param userId
    */
    @ApiOperation(value = "查询用户地址列表")
    @RequestMapping(value = "/userAddress", method = RequestMethod.GET)
    public Result getReceiveAddressByUserId(Long userId){
        return Result.SUCCESS(receiveAddressService.getUserAddress(userId));
    }

    /**
     * 查询用户地址列表
     * @param id
     */
    @ApiOperation(value = "查询用户地址列表")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result getReceiveAddressById(Long id){
        return Result.SUCCESS(receiveAddressService.getById(id));
    }

    /**
    * 查询全部
    * @param param 查询条件
    */
    @ApiOperation(value = "全部查询", notes = "查询ReceiveAddress全部数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result getReceiveAddressAll(@RequestBody HashMap<String, Object> param){
        List<ReceiveAddress> result= receiveAddressService.listByMap(param);
        return Result.SUCCESS(result);
    }

    /**
    * 分页查询
    * @param param 查询条件
    */
    @ApiOperation(value = "分页查询", notes = "分页查询ReceiveAddress全部数据")
    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public Result getReceiveAddressPage(@RequestBody HashMap<String, Object> param){
        PageHelper.startPage(Integer.valueOf(param.get("pageNum").toString()), Integer.valueOf(param.get("pageSize").toString()));
        //手动构建查询条件
        List<ReceiveAddress> result= receiveAddressService.list();
        PageInfo pageInfo = new PageInfo(result);
        return Result.SUCCESS(pageInfo);
    }

    /**
    * 更新保存单条数据
    * @param receiveAddress
    */
    @ApiOperation(value = "更新保存单条数据")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result addOrUpdateReceiveAddress(@RequestBody ReceiveAddress receiveAddress){
        return Result.SUCCESS(receiveAddressService.saveOrUpdate(receiveAddress));
    }

    /**
    * 批量删除
    * @param ids
    */
    @ApiOperation(value = "批量删除数据")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result deleteReceiveAddressById(List<Long> ids){
        return Result.SUCCESS(receiveAddressService.removeByIds(ids));
    }

}
