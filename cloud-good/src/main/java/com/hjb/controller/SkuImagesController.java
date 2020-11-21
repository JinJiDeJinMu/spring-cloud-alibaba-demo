package com.hjb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjb.domain.po.SkuImages;
import com.hjb.service.SkuImagesService;
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
 * SkuImages前端控制器
 * </p>
 *
 * @author jinmu
 * @since 2020-11-20
 */
@RestController
@Api(tags = "SkuImages")
@RequestMapping("/skuImages")
public class SkuImagesController {

    @Autowired
    public SkuImagesService skuImagesService;

    /**
    * 根据主键id查询单条
    * @param id
    */
    @ApiOperation(value = "获取单条数据")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result getSkuImagesById(Long id){
        return Result.SUCCESS(skuImagesService.getById(id));
    }

    /**
    * 查询全部
    * @param param 查询条件
    */
    @ApiOperation(value = "全部查询", notes = "查询SkuImages全部数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result getSkuImagesAll(@RequestBody HashMap<String, Object> param){
        List<SkuImages> result= skuImagesService.listByMap(param);
        return Result.SUCCESS(result);
    }

    /**
    * 分页查询
    * @param param 查询条件
    */
    @ApiOperation(value = "分页查询", notes = "分页查询SkuImages全部数据")
    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public Result getSkuImagesPage(@RequestBody HashMap<String, Object> param){
        PageHelper.startPage(Integer.valueOf(param.get("pageNum").toString()), Integer.valueOf(param.get("pageSize").toString()));
        //手动构建查询条件
        List<SkuImages> result= skuImagesService.list();
        PageInfo pageInfo = new PageInfo(result);
        return Result.SUCCESS(pageInfo);
    }

    /**
    * 更新保存单条数据
    * @param skuImages
    */
    @ApiOperation(value = "更新保存单条数据")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result addOrUpdateSkuImages(@RequestBody SkuImages skuImages){
        return Result.SUCCESS(skuImagesService.saveOrUpdate(skuImages));
    }

    /**
    * 批量删除
    * @param ids
    */
    @ApiOperation(value = "批量删除数据")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result deleteSkuImagesById(List<Long> ids){
        return Result.SUCCESS(skuImagesService.removeByIds(ids));
    }

}
