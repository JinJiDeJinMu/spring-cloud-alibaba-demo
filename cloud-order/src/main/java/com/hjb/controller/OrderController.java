package com.hjb.controller;

import com.alibaba.fastjson.JSON;
import com.hjb.domain.dto.GoodsDTO;
import com.hjb.domain.dto.OrderDTO;
import com.hjb.domain.po.Orders;
import com.hjb.service.OrdersService;
import com.hjb.service.feign.GoodsFeignService;
import com.hjb.util.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RefreshScope
public class OrderController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private GoodsFeignService goodsFeignService;

    @GetMapping(value = "/order/list")
    public Result list(){
        List<Orders> orderPOList = ordersService.list();
        List<OrderDTO> orderDTOList = orderPOList.stream().map(e->{
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(e,orderDTO);
            return orderDTO;
        }).collect(Collectors.toList());

        return Result.SUCCESS(orderDTOList);
    }

    @GetMapping(value = "/order/submit")
    public Result submit(Long goodId){
        Result result = goodsFeignService.get(goodId);
        GoodsDTO goodsDTO = JSON.parseObject(JSON.toJSONString(result.getData()),GoodsDTO.class);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setGoodId(goodId);
        orderDTO.setMount(2);
        orderDTO.setOrderSn(UUID.randomUUID().toString());
        orderDTO.setMoney(goodsDTO.getMoney().multiply(new BigDecimal(2)));
        orderDTO.setCreateTime(LocalDateTime.now());

        Orders orders = new Orders();
        BeanUtils.copyProperties(orderDTO,orders);

        ordersService.save(orders);

        return Result.SUCCESS();
    }

}
