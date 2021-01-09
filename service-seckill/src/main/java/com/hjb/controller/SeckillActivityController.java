package com.hjb.controller;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.hjb.domain.dto.SeckillActivityDTO;
import com.hjb.domain.param.ActivityParam;
import com.hjb.service.SeckillActivityService;
import com.hjb.domain.po.SeckillActivity;
import com.hjb.util.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * SeckillActivitycontroller
 * </p>
 *
 * @author jinmu
 * @date 2020-12-29
 */
@RestController
@Api(tags = "SeckillActivity")
@RequestMapping("/seckillActivity")
public class SeckillActivityController {

    @Autowired
    public SeckillActivityService seckillActivityService;

    /**
    * 根据主键id查询单条
    * @param id
    */
    @ApiOperation(value = "获取单条数据")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result getSeckillActivityById(Long id){
        return Result.SUCCESS(seckillActivityService.getById(id));
    }

    /**
    * 查询全部
    * @param param 查询条件
    */
    @ApiOperation(value = "全部查询", notes = "查询SeckillActivity全部数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result getSeckillActivityAll(@RequestBody HashMap<String, Object> param){
        List<SeckillActivity> result= seckillActivityService.listByMap(param);
        return Result.SUCCESS(result);
    }

    /**
    * 分页查询
    * @param param 查询条件
    */
    @ApiOperation(value = "分页查询", notes = "分页查询SeckillActivity全部数据")
    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public Result getSeckillActivityPage(@RequestBody HashMap<String, Object> param){
        PageHelper.startPage(Integer.valueOf(param.get("pageNum").toString()), Integer.valueOf(param.get("pageSize").toString()));
        //手动构建查询条件
        List<SeckillActivity> result= seckillActivityService.list();
        PageInfo pageInfo = new PageInfo(result);
        return Result.SUCCESS(pageInfo);
    }

    /**
    * 更新保存单条数据
    * @param seckillActivity
    */
    @ApiOperation(value = "更新保存单条数据")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result addOrUpdateSeckillActivity(@RequestBody SeckillActivity seckillActivity){
        return Result.SUCCESS(seckillActivityService.saveOrUpdate(seckillActivity));
    }

    /**
    * 批量删除
    * @param ids
    */
    @ApiOperation(value = "批量删除数据")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result deleteSeckillActivityById(List<Long> ids){
        return Result.SUCCESS(seckillActivityService.removeByIds(ids));
    }

    @ApiOperation(value = "创建秒杀活动")
    @RequestMapping(value = "/created", method = RequestMethod.POST)
    public Result createdKillActivity(@RequestBody @Validated ActivityParam activityParam){
        return Result.SUCCESS(seckillActivityService.createdSeckillActivity(activityParam));
    }

    @ApiOperation(value = "冻结秒杀活动")
    @RequestMapping(value = "/stop", method = RequestMethod.POST)
    public Result stopActivity(Long id){
        return Result.SUCCESS(seckillActivityService.stopActivity(id));
    }


    @ApiOperation(value = "同步秒杀活动到redis")
    @RequestMapping(value = "/sync", method = RequestMethod.POST)
    public Result syncToRedis(Long activityId, Long skuId){
        return Result.SUCCESS(seckillActivityService.syncActivity(activityId,skuId));
    }

    @ApiOperation(value = "获取所有秒杀活动")
    @RequestMapping(value = "/skill-list", method = RequestMethod.GET)
    public Result list(){
        return Result.SUCCESS(seckillActivityService.skillList());
    }


    @ApiOperation(value = "秒杀商品")
    @RequestMapping(value = "/{path}/skill", method = RequestMethod.POST)
    public Result kill(Long activityId, Long skuId, Integer num,@PathVariable("path") String path){
        return Result.SUCCESS(seckillActivityService.kill(activityId, skuId, num,path));
    }

    @ApiOperation(value = "获取秒杀地址")
    @RequestMapping(value = "/skill-url", method = RequestMethod.GET)
    public Result getKillUrl(Long activityId){

        return Result.SUCCESS(seckillActivityService.killUrl(activityId));
    }
}
