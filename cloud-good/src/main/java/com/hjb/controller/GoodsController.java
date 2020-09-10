package com.hjb.controller;

import com.hjb.domain.DTO.GoodsDTO;
import com.hjb.domain.PO.GoodsPO;
import com.hjb.repository.GoodsRepository;
import com.hjb.util.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/service-good")
public class GoodsController {

    @Autowired
    private GoodsRepository goodsRepository;

    @GetMapping(value = "/list")
    public Result list(){

        List<GoodsPO> goodsPOList = goodsRepository.findAll();

        List<GoodsDTO> goodsDTOList = goodsPOList.stream().map(e->{
            GoodsDTO goodsDTO = new GoodsDTO();
            BeanUtils.copyProperties(e,goodsDTO);
            return goodsDTO;
        }).collect(Collectors.toList());
        return Result.SUCCESS(goodsDTOList);
    }

    @PostMapping(value = "/good")
    public Result add(@RequestBody GoodsDTO goodsDTO){

        GoodsPO goodsPO = new GoodsPO();
        BeanUtils.copyProperties(goodsDTO,goodsPO);
        goodsPO.setCreateTime(LocalDateTime.now());

        return Result.SUCCESS(goodsRepository.save(goodsPO));
    }

    @GetMapping(value = "/good/{id}")
    public Result get(@PathVariable("id") Long id){
        GoodsPO goodsPO = goodsRepository.getOne(id);

        GoodsDTO goodsDTO = new GoodsDTO();
        BeanUtils.copyProperties(goodsPO,goodsDTO);
        return Result.SUCCESS(goodsDTO);
    }
}
