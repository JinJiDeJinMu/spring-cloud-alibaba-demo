package com.hjb.controller;
import com.hjb.service.UserCouponService;
import com.hjb.domain.UserCoupon;
import com.hjb.util.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
 * UserCouponcontroller
 * </p>
 *
 * @author jinmu
 * @date 2020-12-26
 */
@RestController
@Api(tags = "UserCoupon")
@RequestMapping("/userCoupon")
public class UserCouponController {

    @Autowired
    public UserCouponService userCouponService;

    /**
    * 根据主键id查询单条
    * @param id
    */
    @ApiOperation(value = "获取单条数据")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result getUserCouponById(Long id){
        return Result.SUCCESS(userCouponService.getById(id));
    }

    /**
    * 查询全部
    * @param param 查询条件
    */
    @ApiOperation(value = "全部查询", notes = "查询UserCoupon全部数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result getUserCouponAll(@RequestBody HashMap<String, Object> param){
        List<UserCoupon> result= userCouponService.listByMap(param);
        return Result.SUCCESS(result);
    }

    /**
    * 分页查询
    * @param param 查询条件
    */
    @ApiOperation(value = "分页查询", notes = "分页查询UserCoupon全部数据")
    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public Result getUserCouponPage(@RequestBody HashMap<String, Object> param){
        PageHelper.startPage(Integer.valueOf(param.get("pageNum").toString()), Integer.valueOf(param.get("pageSize").toString()));
        //手动构建查询条件
        List<UserCoupon> result= userCouponService.list();
        PageInfo pageInfo = new PageInfo(result);
        return Result.SUCCESS(pageInfo);
    }

    /**
    * 更新保存单条数据
    * @param userCoupon
    */
    @ApiOperation(value = "更新保存单条数据")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result addOrUpdateUserCoupon(@RequestBody UserCoupon userCoupon){
        return Result.SUCCESS(userCouponService.saveOrUpdate(userCoupon));
    }

    /**
    * 批量删除
    * @param ids
    */
    @ApiOperation(value = "批量删除数据")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result deleteUserCouponById(List<Long> ids){
        return Result.SUCCESS(userCouponService.removeByIds(ids));
    }

}
