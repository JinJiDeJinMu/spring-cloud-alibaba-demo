package com.hjb.controller;

import com.alibaba.fastjson.JSONObject;
import com.hjb.elastic.EsService;
import com.hjb.elastic.EsTools;
import com.hjb.elastic.model.Query;
import com.hjb.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 商品查询接口
 * @Author JinMu
 * @Date 2020-11-26
 */
@RestController
@RequestMapping("search")
@Api("商品查询相关接口")
public class SearchController {

    @Autowired
    private EsService esService;

    @Autowired
    private EsTools esTools;
    /**
     * 简单查询商品
     * @param query
     * @return
     */
    @ApiOperation("简单查询接口")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Result query(@RequestBody Query query){
        return Result.SUCCESS(esService.search(query));
    }


    /**
     * 查询指定商品的推荐商品
     * @param id
     * @return
     */
    @ApiOperation("查询指定商品的相关商品")
    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    public Result queryRecommend(Long id){
        return Result.SUCCESS(esService.recommend(id));
    }

    /**
     * 查询相关商品
     * @param keyword
     * @return
     */
    @RequestMapping(value = "/relation", method = RequestMethod.GET)
    public Result queryRelation(String keyword){
        return Result.SUCCESS(esService.query(keyword));
    }

}
