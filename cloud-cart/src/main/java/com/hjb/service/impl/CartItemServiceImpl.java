package com.hjb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hjb.domain.dto.CartItemVo;
import com.hjb.domain.dto.CartVo;
import com.hjb.domain.CartItem;
import com.hjb.domain.GoodsInfo;
import com.hjb.domain.SkuInfo;
import com.hjb.execption.good.GoodsException;
import com.hjb.feign.GoodsFeignService;
import com.hjb.mapper.CartItemMapper;
import com.hjb.service.CartItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjb.util.SecurityUserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jinmu
 * @since 2020-12-09
 */
@Service
public class CartItemServiceImpl extends ServiceImpl<CartItemMapper, CartItem> implements CartItemService {

    @Autowired
    private GoodsFeignService goodsFeignService;

    @Autowired
    private SecurityUserUtils securityUserUtils;

    @Override
    public boolean addCart(Long skuId,Long number) {

        //获取当前用户
        Map<String, String> userInfo = securityUserUtils.getUserInfo();

        CartItem cartItem = this.getOne(new LambdaQueryWrapper<CartItem>()
                .eq(CartItem::getUserId,  userInfo.get("id"))
                .eq(CartItem::getSkuId, skuId));

        if(cartItem == null){
            SkuInfo skuInfo = goodsFeignService.getSkuInfoById(skuId);
            if(skuInfo == null){
                throw new GoodsException("商品不存在",500);
            }

            GoodsInfo goodsInfo = goodsFeignService.goods(skuInfo.getGoodsId());
            if(goodsInfo == null){
                throw new GoodsException("商品不存在",500);
            }
            cartItem = new CartItem();

            cartItem.setCreateTime(new Date());
            cartItem.setSkuDesc(skuInfo.getSkuDesc());
            cartItem.setSkuId(skuId);
            cartItem.setSkuImg(skuInfo.getSkuImg());
            cartItem.setBrandId(goodsInfo.getBrandId());
            cartItem.setBrandName(goodsInfo.getBrandName());
            cartItem.setGoodsImg(goodsInfo.getMainImgUrl());
            cartItem.setGoodsId(goodsInfo.getId());
            cartItem.setPrice(skuInfo.getPrice());
            cartItem.setNumber(number);
            cartItem.setIsSelect(0);
            cartItem.setUserId(Long.valueOf(userInfo.get("id")));
            cartItem.setGoodsName(goodsInfo.getGoodsName());

        }else {
            cartItem.setNumber(cartItem.getNumber() + number);
        }
        saveOrUpdate(cartItem);
        return Boolean.TRUE;
    }

    @Override
    public boolean deleteCarts(List<Long> ids) {
        return this.removeByIds(ids);
    }

    @Override
    public List<CartVo> getCart(Long userId) {
        List<CartVo> cartVos = new ArrayList<>();

        List<CartItem> cartItems = this.list(new LambdaQueryWrapper<CartItem>()
                .eq(CartItem::getUserId, userId));

        Map<Long, List<CartItem>> listMap = cartItems.stream()
                .collect(Collectors.groupingBy(CartItem::getBrandId));

        listMap.entrySet().forEach(e->{

            CartVo cartVo = new CartVo();
            List<CartItem> cartItemList = e.getValue();
            List<CartItemVo> collect = cartItemList.stream().map(x -> {
                CartItemVo cartItemVo = new CartItemVo();
                BeanUtils.copyProperties(x, cartItemVo);
                return cartItemVo;
            }).collect(Collectors.toList());

            cartVo.setBrandId( e.getKey());
            cartVo.setBrandName(cartItemList.get(0).getBrandName());
            cartVo.setCartItemVos(collect);

            cartVos.add(cartVo);
        });

        return cartVos;
    }
}
