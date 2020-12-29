package com.hjb.controller;
import com.hjb.service.SeckillGoodsService;
import com.hjb.domain.po.SeckillGoods;
import com.hjb.util.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * SeckillGoodscontroller
 * </p>
 *
 * @author jinmu
 * @date 2020-12-29
 */
@RestController
@Api(tags = "SeckillGoods")
@RequestMapping("/seckillGoods")
public class SeckillGoodsController {

    @Autowired
    public SeckillGoodsService seckillGoodsService;

    /**
    * 根据主键id查询单条
    * @param id
    */
    @ApiOperation(value = "获取单条数据")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result getSeckillGoodsById(Long id){
        return Result.SUCCESS(seckillGoodsService.getById(id));
    }

    /**
    * 查询全部
    * @param param 查询条件
    */
    @ApiOperation(value = "全部查询", notes = "查询SeckillGoods全部数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result getSeckillGoodsAll(@RequestBody HashMap<String, Object> param){
        List<SeckillGoods> result= seckillGoodsService.listByMap(param);
        return Result.SUCCESS(result);
    }

    /**
    * 分页查询
    * @param param 查询条件
    */
    @ApiOperation(value = "分页查询", notes = "分页查询SeckillGoods全部数据")
    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public Result getSeckillGoodsPage(@RequestBody HashMap<String, Object> param){
        PageHelper.startPage(Integer.valueOf(param.get("pageNum").toString()), Integer.valueOf(param.get("pageSize").toString()));
        //手动构建查询条件
        List<SeckillGoods> result= seckillGoodsService.list();
        PageInfo pageInfo = new PageInfo(result);
        return Result.SUCCESS(pageInfo);
    }

    /**
    * 更新保存单条数据
    * @param seckillGoods
    */
    @ApiOperation(value = "更新保存单条数据")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result addOrUpdateSeckillGoods(@RequestBody SeckillGoods seckillGoods){
        return Result.SUCCESS(seckillGoodsService.saveOrUpdate(seckillGoods));
    }

    /**
    * 批量删除
    * @param ids
    */
    @ApiOperation(value = "批量删除数据")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result deleteSeckillGoodsById(List<Long> ids){
        return Result.SUCCESS(seckillGoodsService.removeByIds(ids));
    }
}
