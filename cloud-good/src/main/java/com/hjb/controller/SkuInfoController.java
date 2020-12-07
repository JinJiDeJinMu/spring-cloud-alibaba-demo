package com.hjb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjb.domain.po.SkuInfo;
import com.hjb.elastic.EsService;
import com.hjb.service.SkuInfoService;
import com.hjb.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * SkuInfo前端控制器
 * </p>
 *
 * @author jinmu
 * @since 2020-11-20
 */
@RestController
@Api(tags = "SkuInfo")
@RequestMapping(value = "/skuInfo")
public class SkuInfoController {

    @Autowired
    private SkuInfoService skuInfoService;

    /**
    * 根据主键id查询单条
    * @param id
    */
    @ApiOperation(value = "获取单条数据")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result getSkuInfoById(@RequestParam(value = "id") Long id){
        return Result.SUCCESS(skuInfoService.getById(id));
    }

    /**
    * 查询全部
    * @param param 查询条件
    */
    @ApiOperation(value = "全部查询", notes = "查询SkuInfo全部数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result getSkuInfoAll(@RequestBody HashMap<String, Object> param){
        List<SkuInfo> result= skuInfoService.listByMap(param);
        return Result.SUCCESS(result);
    }

    /**
    * 分页查询
    * @param param 查询条件
    */
    @ApiOperation(value = "分页查询", notes = "分页查询SkuInfo全部数据")
    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public Result getSkuInfoPage(@RequestBody HashMap<String, Object> param){
        PageHelper.startPage(Integer.valueOf(param.get("pageNum").toString()), Integer.valueOf(param.get("pageSize").toString()));
        //手动构建查询条件
        List<SkuInfo> result= skuInfoService.list();
        PageInfo pageInfo = new PageInfo(result);
        return Result.SUCCESS(pageInfo);
    }


    /**
    * 更新或者保存商品SKU
    * @param skuInfo
    */
    @ApiOperation(value = "更新保存单条数据")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result addOrUpdateSkuInfo(@RequestBody SkuInfo skuInfo){
        return Result.SUCCESS(skuInfoService.addOrUpdateSkuInfo(skuInfo));
    }

    /**
    * 批量删除
    * @param ids
    */
    @ApiOperation(value = "批量删除数据")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result deleteSkuInfoById(List<Long> ids){
        return Result.SUCCESS(skuInfoService.removeByIds(ids));
    }


    /**
     * 库存释放
     * @param skuInfo
     * @param number
     * @return
     */
    @ApiOperation(value = "释放库存")
    @RequestMapping(value = "/addCount",method = RequestMethod.POST)
    public Result addSkuCount(@RequestBody SkuInfo skuInfo, Long number){
        return Result.SUCCESS(skuInfoService.addSKUCount(skuInfo, number));
    }


    /**
     * 扣除库存
     * @param skuInfo
     * @param number
     * @return
     */
    @ApiOperation(value = "扣除库存")
    @RequestMapping(value = "/reduceCount",method = RequestMethod.POST)
    public Result reduceSkuCount(@RequestBody SkuInfo skuInfo, Long number){
        return Result.SUCCESS(skuInfoService.reduceSKUCount(skuInfo,number));
    }

}
