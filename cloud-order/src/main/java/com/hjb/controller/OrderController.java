package com.hjb.controller;

import com.alibaba.fastjson.JSON;
import com.hjb.domain.DTO.GoodsDTO;
import com.hjb.domain.DTO.OrderDTO;
import com.hjb.domain.PO.OrderPO;
import com.hjb.service.GoodsFeignService;
import com.hjb.service.OrderService;
import com.hjb.util.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/service-order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsFeignService goodsFeignService;

    @GetMapping(value = "/list")
    public Result list(){
        List<OrderPO> orderPOList = orderService.list();
        List<OrderDTO> orderDTOList = orderPOList.stream().map(e->{
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(e,orderDTO);
            return orderDTO;
        }).collect(Collectors.toList());

        return Result.SUCCESS(orderDTOList);
    }

    @GetMapping(value = "/submit")
    public Result submit(Long goodId){

        Result result = goodsFeignService.get(goodId);

        GoodsDTO goodsDTO = JSON.parseObject(JSON.toJSONString(result.getData()),GoodsDTO.class);

        System.out.println("==="+goodsDTO);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setGoodId(goodId);
        orderDTO.setMount(2);
        orderDTO.setOrderSn(UUID.randomUUID().toString());
        orderDTO.setMoney(goodsDTO.getMoney().multiply(new BigDecimal(2)));
        orderDTO.setCreateTime(LocalDateTime.now());

        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(orderDTO,orderPO);
        orderService.save(orderPO);

        return Result.SUCCESS();
    }
}
