package com.hjb.controller;

import com.alibaba.fastjson.JSONObject;
import com.hjb.elastic.EsService;
import com.hjb.elastic.EsTools;
import com.hjb.elastic.model.Query;
import com.hjb.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 商品查询接口
 * @Author JinMu
 * @Date 2020-11-26
 */
@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private EsService esService;

    @Autowired
    private EsTools esTools;
    /**
     * 查询商品
     * @param query
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Result query(Query query){
        return Result.SUCCESS(esService.search(query));
    }

    @GetMapping(value = "/test")
    public Result test(String keyword){

        return Result.SUCCESS(esTools.search("goodsku","goodsDesc",keyword));
    }

    public static void main(String[] args) {
        HashMap<String,Object> hashMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("白色");
        list.add("黑色");
        hashMap.put("颜色",list);
        System.out.println(JSONObject.toJSONString(hashMap));
        System.out.println("{\"颜色\":[\"白色\",\"黑色\"]}");
    }
}
