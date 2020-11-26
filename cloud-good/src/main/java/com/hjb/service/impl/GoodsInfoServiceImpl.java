package com.hjb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjb.domain.dto.GoodsDetailDTO;
import com.hjb.domain.dto.SkuInfoDTO;
import com.hjb.domain.param.GoodsInfoParam;
import com.hjb.domain.po.GoodsAttribute;
import com.hjb.domain.po.GoodsInfo;
import com.hjb.domain.po.SkuInfo;
import com.hjb.elastic.ElasticDocument;
import com.hjb.elastic.EsService;
import com.hjb.elastic.model.EsGoods;
import com.hjb.elastic.model.Query;
import com.hjb.mapper.GoodsInfoMapper;
import com.hjb.service.*;
import com.hjb.util.Result;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
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
    public Result save(GoodsInfoParam goodsInfoParam) {

        //保存商品信息
        GoodsInfo goodsInfo = new GoodsInfo();
        BeanUtils.copyProperties(goodsInfoParam,goodsInfo);
        goodsInfo.setCreatedTime(LocalDateTime.now());
        goodsInfo.setUpdateTime(LocalDateTime.now());
        save(goodsInfo);

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

        //添加到es中
        EsGoods esGoods = new EsGoods();
        BeanUtils.copyProperties(goodsInfo,esGoods);

        BigDecimal min_price = skuInfos.stream().
                map(SkuInfo::getPrice).
                min(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
        esGoods.setPrice(min_price);
        esGoods.setSaleCount(0l);
        esGoods.setGoodsAttributes(goodsAttributes);
        esGoods.setSkuInfos(skuInfos);
        System.out.println("esGoods=" + esGoods);
        esService.insertData("goodsku",esGoods.getId(),esGoods);

        return Result.SUCCESS();
    }

    @Override
    public Result detail(Long id) {
        GoodsInfo goodsInfo = getById(id);

        if(goodsInfo == null){
            return Result.FAILURE("查询商品不存在");
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


        return Result.SUCCESS(goodsDetailDTO);
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

}
