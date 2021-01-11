package com.hjb.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjb.domain.dto.GoodsDetailDTO;
import com.hjb.domain.dto.SkuInfoDTO;
import com.hjb.domain.param.GoodsInfoParam;
import com.hjb.domain.GoodsAttribute;
import com.hjb.domain.GoodsInfo;
import com.hjb.domain.SkuInfo;
import com.hjb.elastic.ElasticDocument;
import com.hjb.elastic.EsService;
import com.hjb.elastic.model.Goods;
import com.hjb.execption.good.GoodsException;
import com.hjb.mapper.GoodsInfoMapper;
import com.hjb.service.*;
import com.hjb.util.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  商品服务实现类
 * </p>
 *
 * @author jinmu
 * @since 2020-11-20
 */
@Service
public class GoodsInfoServiceImpl extends ServiceImpl<GoodsInfoMapper, GoodsInfo> implements GoodsInfoService {

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private GoodsAttributeService goodsAttributeService;

    @Autowired
    private EsService esService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean save(GoodsInfoParam goodsInfoParam) {

        //保存商品信息
        GoodsInfo goodsInfo = new GoodsInfo();
        BeanUtils.copyProperties(goodsInfoParam,goodsInfo);
        goodsInfo.setCreatedTime(LocalDateTime.now());
        goodsInfo.setUpdateTime(LocalDateTime.now());
        this.save(goodsInfo);

        //保存商品属性
        List<GoodsAttribute> goodsAttributes = goodsInfoParam.getGoodsAttributeParamList()
                .stream().map(e->{
                    GoodsAttribute goodsAttribute = new GoodsAttribute();
                    BeanUtils.copyProperties(e,goodsAttribute);
                    goodsAttribute.setCreateTime(LocalDateTime.now());
                    goodsAttribute.setGoodsId(goodsInfo.getId());
                    goodsAttribute.setCategoryId(goodsInfo.getCategoryId());
                    return goodsAttribute;
                }).collect(Collectors.toList());
        goodsAttributeService.saveBatch(goodsAttributes);

        //保存商品SKU
        List<SkuInfo> skuInfos = goodsInfoParam.getSkuInfoParamList()
                .stream().map(e->{
                    SkuInfo skuInfo = new SkuInfo();
                    BeanUtils.copyProperties(e,skuInfo);
                    skuInfo.setGoodsId(goodsInfo.getId());
                    return skuInfo;
                }).collect(Collectors.toList());
        skuInfoService.saveBatch(skuInfos);

        //保存到es
        toEs(goodsInfo,skuInfos,goodsAttributes);

        return Boolean.TRUE;
    }

    //@GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GoodsDetailDTO detail(Long id) {
        GoodsInfo goodsInfo = getById(id);

        if(goodsInfo == null){
            throw new GoodsException("商品不存在",500);
        }
        GoodsDetailDTO goodsDetailDTO = new GoodsDetailDTO();

        BeanUtils.copyProperties(goodsInfo,goodsDetailDTO);

        List<GoodsAttribute> goodsAttributes = goodsAttributeService.list(new LambdaQueryWrapper<GoodsAttribute>()
                .eq(GoodsAttribute::getGoodsId,id)
                .orderByAsc(GoodsAttribute::getAttrSort));

        //查询商品属性
        List<String> attrs = goodsAttributes.stream().map(e->{
            return e.getAttrValue();
        }).collect(Collectors.toList());
        goodsDetailDTO.setAttrs(attrs);

        //查询商品SKU
        List<SkuInfo> skuInfos = skuInfoService.list(new LambdaQueryWrapper<SkuInfo>()
                .eq(SkuInfo::getGoodsId,id));

        List<SkuInfoDTO> skuInfoDTOS = skuInfos.stream().map(e->{
            SkuInfoDTO skuInfoDTO = new SkuInfoDTO();
            BeanUtils.copyProperties(e,skuInfoDTO);

            return skuInfoDTO;
        }).collect(Collectors.toList());
        goodsDetailDTO.setSkuInfoDTOS(skuInfoDTOS);

        return goodsDetailDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteGoods(List<Long> ids) {

        removeByIds(ids);
        //删除商品属性
        goodsAttributeService.deleteGoodsAttrByGoodsId(ids);
        //删除商品SKU
        skuInfoService.deleteSKUByGoodsId(ids);
        //删除es
        ids.forEach(e->{
            esService.deleteData(ElasticDocument.INDEX,e);
        });
        return Boolean.TRUE;
    }

    private  boolean toEs(GoodsInfo goodsInfo,List<SkuInfo> skuInfos,List<GoodsAttribute> goodsAttributes){
        Goods goods = new Goods();

        BeanUtils.copyProperties(goodsInfo, goods);

        List<Goods.Attribute> collect = goodsAttributes.stream().map(x -> {
            Goods.Attribute attribute = new Goods.Attribute();

            attribute.setId(x.getId());
            JSONObject jsonObject = JSON.parseObject(x.getAttrValue());
            attribute.setName(jsonObject.getString("key"));
            attribute.setValue(jsonObject.getJSONArray("value").toJavaList(String.class));

            return attribute;

        }).collect(Collectors.toList());

        goods.setGoodsAttributes(collect);

        BigDecimal min_price = skuInfos.stream().
                map(SkuInfo::getPrice).
                min(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
        goods.setPrice(min_price);
        goods.setSaleCount(0l);

        return esService.insertData(ElasticDocument.INDEX, goods.getId(), goods);
    }
}
