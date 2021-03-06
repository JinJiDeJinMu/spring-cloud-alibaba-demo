package com.hjb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjb.domain.param.OrderParam;
import com.hjb.domain.po.Order;
import com.hjb.feign.GoodsFeignService;
import com.hjb.feign.UserFeignService;
import com.hjb.service.OrderService;
import com.hjb.util.Result;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * Order前端控制器
 * </p>
 *
 * @author jinmu
 * @since 2020-11-23
 */
@RestController
@Api(tags = "Order")
@RequestMapping("/order")
@RefreshScope
public class OrderController {

    @Autowired
    public OrderService orderService;

    @Autowired
    private GoodsFeignService goodsFeignService;

    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private DefaultMQProducer producer;

    @Autowired
    private RedissonClient redissonClient;

    /**
    * 根据主键id查询单条
    * @param id
    */
    @ApiOperation(value = "获取单条数据")
    @GlobalTransactional
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result getOrderById(Long id){
        return Result.SUCCESS(orderService.getById(id));
    }

    /**
    * 查询全部
    * @param param 查询条件
    */
    @ApiOperation(value = "全部查询", notes = "查询Order全部数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result getOrderAll(@RequestBody HashMap<String, Object> param){
        List<Order> result= orderService.listByMap(param);
        return Result.SUCCESS(result);
    }

    /**
    * 分页查询
    * @param param 查询条件
    */
    @ApiOperation(value = "分页查询", notes = "分页查询Order全部数据")
    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public Result getOrderPage(@RequestBody HashMap<String, Object> param){
        PageHelper.startPage(Integer.valueOf(param.get("pageNum").toString()), Integer.valueOf(param.get("pageSize").toString()));
        //手动构建查询条件
        List<Order> result= orderService.list();
        PageInfo pageInfo = new PageInfo(result);
        return Result.SUCCESS(pageInfo);
    }

    /**
    *
    * @param order
    */
    @ApiOperation(value = "订单提交")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result submit(@RequestBody OrderParam order){
        return Result.SUCCESS(orderService.submit(order));
    }

    /**
     * 购买校验
     * @param orderParam
     * @return
     */
    public Result buyConfirm(OrderParam orderParam){
        return orderService.buyConfirm(orderParam);
    }

    /**
     * 订单发货
     * @param id
     * @param deliveryCompany
     * @param deliverySn
     * @return
     */
    public Result orderSendGoods(Long id, String deliveryCompany, String deliverySn){
        return Result.SUCCESS(orderService.orderSendGoods(id,deliveryCompany,deliverySn));
    }

}
