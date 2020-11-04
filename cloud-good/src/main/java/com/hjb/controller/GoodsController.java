package com.hjb.controller;

import com.hjb.domain.dto.GoodsDTO;
import com.hjb.domain.po.Goods;
import com.hjb.service.GoodsService;
import com.hjb.util.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RefreshScope
@RequestMapping(value = "/api/v1/service-good")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;


    @GetMapping(value = "/list")
    public Result list(){

        List<Goods> goodsList = goodsService.list();

        List<GoodsDTO> goodsDTOS = goodsList.stream().map(e->{
            GoodsDTO goodsDTO = new GoodsDTO();
            BeanUtils.copyProperties(e,goodsDTO);
            return goodsDTO;
        }).collect(Collectors.toList());

        return Result.SUCCESS(goodsDTOS);
    }

    @PostMapping(value = "/good")
    public Result add(@RequestBody GoodsDTO goodsDTO){

        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsDTO,goods);
        goods.setCreateTime(LocalDateTime.now());

        goodsService.saveOrUpdate(goods);
        return Result.SUCCESS();
    }

    @GetMapping(value = "/good")
    public Result get(@RequestParam(value = "id") Long id){
        Goods goods = goodsService.getById(id);

        GoodsDTO goodsDTO = new GoodsDTO();
        BeanUtils.copyProperties(goods,goodsDTO);
        return Result.SUCCESS(goodsDTO);
    }
}
