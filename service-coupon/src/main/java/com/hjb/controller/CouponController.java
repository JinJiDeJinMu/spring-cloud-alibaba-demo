package com.hjb.controller;
import com.hjb.domain.param.CouponParam;
import com.hjb.service.CouponService;
import com.hjb.domain.Coupon;
import com.hjb.util.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
 * Couponcontroller
 * </p>
 *
 * @author jinmu
 * @date 2020-12-26
 */
@RestController
@Api(tags = "Coupon")
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    public CouponService couponService;

    /**
    * 根据主键id查询单条
    * @param id
    */
    @ApiOperation(value = "获取单条数据")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result getCouponById(Long id){
        return Result.SUCCESS(couponService.getById(id));
    }

    /**
    * 查询全部
    * @param param 查询条件
    */
    @ApiOperation(value = "全部查询", notes = "查询Coupon全部数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result getCouponAll(@RequestBody HashMap<String, Object> param){
        List<Coupon> result= couponService.listByMap(param);
        return Result.SUCCESS(result);
    }

    /**
    * 分页查询
    * @param param 查询条件
    */
    @ApiOperation(value = "分页查询", notes = "分页查询Coupon全部数据")
    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public Result getCouponPage(@RequestBody HashMap<String, Object> param){
        PageHelper.startPage(Integer.valueOf(param.get("pageNum").toString()), Integer.valueOf(param.get("pageSize").toString()));
        //手动构建查询条件
        List<Coupon> result= couponService.list();
        PageInfo pageInfo = new PageInfo(result);
        return Result.SUCCESS(pageInfo);
    }

    /**
    * 更新保存单条数据
    * @param coupon
    */
    @ApiOperation(value = "更新保存单条数据")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result addOrUpdateCoupon(@RequestBody Coupon coupon){
        return Result.SUCCESS(couponService.saveOrUpdate(coupon));
    }

    /**
    * 批量删除
    * @param ids
    */
    @ApiOperation(value = "批量删除数据")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result deleteCouponById(List<Long> ids){
        return Result.SUCCESS(couponService.removeByIds(ids));
    }

    /**
     * 创建优惠券
     * @param couponParam
     * @return
     */
    @ApiOperation(value = "创建优惠券")
    @RequestMapping(value = "/created", method = RequestMethod.POST)
    public Result created(@RequestBody @Validated CouponParam couponParam){
        return Result.SUCCESS(couponService.createdCoupon(couponParam));
    }

    /**
     * 冻结优惠券
     * @param id
     * @return
     */
    @ApiOperation(value = "冻结优惠券")
    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public Result stopCoupon(Long id){
        return Result.SUCCESS(couponService.stopCoupon(id));
    }



}
