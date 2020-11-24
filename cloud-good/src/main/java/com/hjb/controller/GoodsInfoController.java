package com.hjb.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjb.domain.param.GoodsInfoParam;
import com.hjb.domain.po.GoodsInfo;
import com.hjb.service.GoodsInfoService;
import com.hjb.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * GoodsInfo前端控制器
 * </p>
 *
 * @author jinmu
 * @since 2020-11-20
 */
@RestController
@Api(tags = "GoodsInfo")
@RequestMapping("/goodsInfo")
public class GoodsInfoController {

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
    * 根据主键id查询单条
    * @param id
    */
    @ApiOperation(value = "获取单条数据")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result getGoodsInfoById(Long id){
        return Result.SUCCESS(goodsInfoService.getById(id));
    }

    /**
    * 查询全部
    * @param param 查询条件
    */
    @ApiOperation(value = "全部查询", notes = "查询GoodsInfo全部数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result getGoodsInfoAll(@RequestBody HashMap<String, Object> param){
        List<GoodsInfo> result= goodsInfoService.listByMap(param);
        return Result.SUCCESS(result);
    }

    /**
    * 分页查询
    * @param param 查询条件
    */
    @ApiOperation(value = "分页查询", notes = "分页查询GoodsInfo全部数据")
    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public Result getGoodsInfoPage(@RequestBody HashMap<String, Object> param){
        PageHelper.startPage(Integer.valueOf(param.get("pageNum").toString()), Integer.valueOf(param.get("pageSize").toString()));
        //手动构建查询条件
        List<GoodsInfo> result= goodsInfoService.list();
        PageInfo pageInfo = new PageInfo(result);
        return Result.SUCCESS(pageInfo);
    }

    /**
     * es查询
     * @param keyword 查询条件
     */
    @ApiOperation(value = "分页查询", notes = "分页查询GoodsInfo全部数据")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result query(String keyword){
        return Result.SUCCESS(goodsInfoService.query(keyword));
    }

    /**
    * 添加商品
    * @param goodsInfoParam
    */
    @ApiOperation(value = "保存单条数据")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody GoodsInfoParam goodsInfoParam){
        return Result.SUCCESS(goodsInfoService.save(goodsInfoParam));
    }

    /**
    * 批量删除
    * @param ids
    */
    @ApiOperation(value = "批量删除数据")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result deleteGoodsInfoById(@RequestBody List<Long> ids){
        return Result.SUCCESS(goodsInfoService.deleteGoods(ids));
    }


    /**
     * 获取商品详情
     * @param id
     * @return
     */
    @ApiOperation(value = "查询商品详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Result goodsDetail(@RequestParam(value = "id") Long id){
        return Result.SUCCESS(goodsInfoService.goodsDetail(id));
    }

    /**
     * 获取商品规格属性
     * @param id
     * @return
     */
    @ApiOperation(value = "查询商品规格属性")
    @RequestMapping(value = "/attrs", method = RequestMethod.GET)
    public Result goodsAttrs(Long id){
        return Result.SUCCESS(goodsInfoService.goodsAttrs(id));
    }

}
