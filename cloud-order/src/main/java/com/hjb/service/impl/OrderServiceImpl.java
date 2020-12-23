package com.hjb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjb.constant.CommonConstants;
import com.hjb.constant.MQTopicConstants;
import com.hjb.constant.OrderStatusConstans;
import com.hjb.domain.param.OrderParam;
import com.hjb.domain.param.OrderTrade;
import com.hjb.domain.param.SkuChange;
import com.hjb.domain.po.*;
import com.hjb.execption.order.OrderException;
import com.hjb.feign.GoodsFeignService;
import com.hjb.feign.UserFeignService;
import com.hjb.mapper.OrderMapper;
import com.hjb.service.OrderItemService;
import com.hjb.service.OrderService;
import com.hjb.util.Result;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 订单服务实现类
 * </p>
 *
 * @author jinmu
 * @since 2020-11-23
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private GoodsFeignService goodsFeignService;

    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private DefaultMQProducer producer;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Result buyConfirm(OrderParam orderParam) {
        OrderTrade orderTrade = new OrderTrade();
        checkOrder(orderParam, orderTrade);

        redisTemplate.opsForValue().set("USER_BUY_" + orderParam.getUserId(),JSONObject.toJSONString(orderTrade));
        return null;
    }

    @GlobalTransactional
    //@Transactional
    @Override
    public Result submit(OrderParam orderParam) {
        OrderTrade orderTrade = new OrderTrade();

        checkOrder(orderParam, orderTrade);

        Order order = createOrder(orderParam, orderTrade);
        save(order);

        OrderItem orderItem = createOrderItem(order, orderTrade, orderParam);
        orderItemService.save(orderItem);

        reduceSkuInfo(orderTrade.getSkuInfo(), orderParam);

        sendMQ(order.getId());

        return Result.SUCCESS();
    }

    /**
     * 订单超时取消(topic=order_cancel)
     * @param orderId
     * @return
     */
    public boolean sendMQ(Long orderId){
        Message message = new Message();
        message.setBody(JSONObject.toJSONString(orderId).getBytes());
        message.setTopic(MQTopicConstants.ORDER_CANCEL_TOPIC.getTopic());
        message.setTags(MQTopicConstants.ORDER_CANCEL_TOPIC.getTags());
        message.setDelayTimeLevel(16);
        SendResult send = null;
        try {
            send = producer.send(message);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       return send.getSendStatus() == SendStatus.SEND_OK;
    }

    /**
     * 扣除库存
     *
     * @param skuInfo
     * @param orderParam
     */
    public void reduceSkuInfo(SkuInfo skuInfo, OrderParam orderParam) {
        RLock lock = redissonClient.getLock("SKU-COUNT-LOCK-" + String.valueOf(skuInfo.getId()));
        try {
            lock.lock(1, TimeUnit.SECONDS);


            Result result = goodsFeignService.reduceSkuCount(skuInfo.getId(), orderParam.getNumber());
            if (result == null || "false".equals(result.getData())) {
                throw new OrderException("库存扣除失败", CommonConstants.ORDER_SKU_MOUNT);
            }
            log.info("扣除库存成功, skuInfoId: {}, 扣除: {}, 还剩: {}", skuInfo.getId(), orderParam.getNumber(), skuInfo.getMount());
        } catch (Exception e) {
            log.info("扣除库存失败, skuInfoId: {}, message: {}", orderParam.getSkuId(), e.getMessage());
            throw new OrderException("库存失败", CommonConstants.ORDER_SKU_MOUNT);
        } finally {
            if (lock.isLocked()) {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
    }

    /**
     * 生成订单明细表
     *
     * @param order
     * @param orderTrade
     * @return
     */
    public OrderItem createOrderItem(Order order, OrderTrade orderTrade, OrderParam orderParam) {

        GoodsInfo goodsInfo = orderTrade.getGoodsInfo();
        SkuInfo skuInfo = orderTrade.getSkuInfo();

        OrderItem orderItem = new OrderItem();

        orderItem.setOrderId(order.getId());
        orderItem.setOrderSn(order.getOrderSn());
        orderItem.setGoodsId(goodsInfo.getId());
        orderItem.setGoodsName(goodsInfo.getGoodsName());
        orderItem.setGoodsPic(goodsInfo.getMainImgUrl());
        orderItem.setBrandId(goodsInfo.getBrandId());
        orderItem.setCategoryId(goodsInfo.getCategoryId());
        orderItem.setCouponAmount(order.getCouponMoney());
        orderItem.setSkuId(skuInfo.getId());
        orderItem.setSkuAttrsVals(skuInfo.getAttrList());
        orderItem.setSkuName(skuInfo.getSkuTitle());
        orderItem.setSkuPic(skuInfo.getSkuImg());
        orderItem.setSkuPrice(skuInfo.getPrice());
        orderItem.setSkuQuantity(orderParam.getNumber().intValue());
        orderItem.setIntegrationAmount(order.getIntegrationMoney());
        orderItem.setPromotionAmount(order.getPromotionMoney());
        orderItem.setRealAmount(order.getPayMoney());

        log.info("生成订单明细成功 orderItem: {}", orderItem);

        return orderItem;
    }

    /**
     * 生成预订单
     *
     * @param orderParam
     * @return
     */
    public Order createOrder(OrderParam orderParam, OrderTrade orderTrade) {

        Order order = new Order();

        Date now = new Date();
        order.setOrderSn(String.valueOf(IdUtil.getSnowflake(1, 1).nextId()));
        order.setUserId(orderParam.getUserId());
        order.setCouponId(orderParam.getCouponId());
        order.setCreateTime(now);
        order.setUsername(orderTrade.getUser().getUserName());
        //订单总金额
        order.setTotalMoney(orderParam.getPrice());
        //邮费
        order.setFreightMoney(BigDecimal.ZERO);
        //优惠券
        order.setCouponMoney(BigDecimal.ZERO);
        //促销优惠
        order.setPromotionMoney(BigDecimal.ZERO);
        //积分抵扣
        order.setIntegrationMoney(BigDecimal.ZERO);
        //后台调整优惠
        order.setDiscountMoney(BigDecimal.ZERO);

        BigDecimal payMoney = order.getTotalMoney().subtract(order.getFreightMoney())
                .subtract(order.getCouponMoney()).subtract(order.getPromotionMoney())
                .subtract(order.getIntegrationMoney()).subtract(order.getDiscountMoney());

        order.setPayMoney(payMoney.intValue() < 0 ? BigDecimal.ZERO : payMoney);
        order.setPayType(1);
        order.setSourceType(0);
        order.setStatus(OrderStatusConstans.WAIT_PAY.getCode());
        ReceiveAddress address = orderTrade.getAddress();
        order.setReceiverName(address.getName());
        order.setReceiverPhone(address.getPhone());
        order.setReceiverPostCode(address.getPostCode());
        order.setReceiverProvince(address.getProvince());
        order.setReceiverCity(address.getCity());
        order.setReceiverDetailAddress(address.getDetailAddress());
        order.setConfirmStatus(0);

        log.info("生成预订单成功 order: {}", order);
        return order;
    }

    /**
     * 检验合法性
     *
     * @param orderParam
     */
    public void checkOrder(OrderParam orderParam, OrderTrade orderTrade) {

        if (orderParam == null) {
            throw new OrderException("订单不合法", CommonConstants.ORDER_CHECK_UN_RIGHT);
        }
        //校验用户信息
        Result userResult = userFeignService.getUserById(orderParam.getUserId());
        if (userResult.getSuccess() == false) {
            throw new OrderException("用户服务出错", CommonConstants.ORDER_CHECK_UN_RIGHT);
        }

        HashMap<String, Object> hashMap_1 = (HashMap<String, Object>) userResult.getData();
        User user = BeanUtil.mapToBean(hashMap_1, User.class, false, CopyOptions.create());
        if (user == null || user.getStatus() == 1) {
            throw new OrderException("用户不存在", CommonConstants.ORDER_CHECK_UN_RIGHT);
        }
        orderTrade.setUser(user);

        //校验收货地址
        Result addressReulst = userFeignService.getReceiveAddressById(orderParam.getAddressId());
        if (addressReulst.getSuccess() == false) {
            throw new OrderException("用户服务出错", CommonConstants.ORDER_CHECK_UN_RIGHT);
        }
        HashMap<String, Object> hashMap_2 = (HashMap<String, Object>) addressReulst.getData();
        ReceiveAddress address = BeanUtil.mapToBean(hashMap_2, ReceiveAddress.class, false, CopyOptions.create());
        if (address == null) {
            throw new OrderException("收货地址不存在", CommonConstants.ORDER_CHECK_UN_RIGHT);
        }
        orderTrade.setAddress(address);

        //商品校验
        Result goodsResult = goodsFeignService.goods(orderParam.getGoodsId());
        if (goodsResult.getSuccess() == false) {
            throw new OrderException("商品服务出错", CommonConstants.ORDER_CHECK_UN_RIGHT);
        }
        HashMap<String, Object> hashMap_3 = (HashMap<String, Object>) goodsResult.getData();
        GoodsInfo goodsInfo = BeanUtil.mapToBean(hashMap_3, GoodsInfo.class, false, CopyOptions.create());
        if (goodsInfo == null || goodsInfo.getIsPublish() == 1) {
            throw new OrderException("找不到指定商品", CommonConstants.ORDER_CHECK_UN_RIGHT);
        }
        orderTrade.setGoodsInfo(goodsInfo);
        //库存校验
        Result skuResult = goodsFeignService.getSkuInfoById(orderParam.getSkuId());
        if (skuResult.getSuccess() == false) {
            throw new OrderException("商品服务出错", CommonConstants.ORDER_CHECK_UN_RIGHT);
        }
        HashMap<String, Object> hashMap_4 = (HashMap<String, Object>) skuResult.getData();
        SkuInfo skuInfo = BeanUtil.mapToBean(hashMap_4, SkuInfo.class, false, CopyOptions.create());
        if (skuInfo == null || skuInfo.getMount() < orderParam.getNumber()) {
            throw new OrderException("商品库存不足", CommonConstants.ORDER_CHECK_UN_RIGHT);
        }
        orderTrade.setSkuInfo(skuInfo);

        BigDecimal money = new BigDecimal(orderParam.getNumber()).multiply(skuInfo.getPrice());
        if (money.compareTo(orderParam.getPrice()) != 0) {
            throw new OrderException("商品价格出错", CommonConstants.ORDER_CHECK_UN_RIGHT);
        }
    }
}
