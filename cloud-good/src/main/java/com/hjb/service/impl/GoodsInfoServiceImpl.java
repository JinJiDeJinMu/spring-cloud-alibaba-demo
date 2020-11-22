package com.hjb.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjb.domain.dto.GoodsDetailDTO;
import com.hjb.domain.dto.SkuInfoDTO;
import com.hjb.domain.param.GoodsInfoParam;
import com.hjb.domain.po.GoodsAttr;
import com.hjb.domain.po.GoodsInfo;
import com.hjb.domain.po.SkuAttr;
import com.hjb.domain.po.SkuInfo;
import com.hjb.mapper.GoodsInfoMapper;
import com.hjb.service.GoodsAttrService;
import com.hjb.service.GoodsInfoService;
import com.hjb.service.SkuInfoService; 
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
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
    private GoodsAttrService goodsAttrService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean save(GoodsInfoParam goodsInfoParam) {
        GoodsInfo goodsInfo = new GoodsInfo();
        BeanUtils.copyProperties(goodsInfoParam,goodsInfo);
        goodsInfo.setCreatedTime(LocalDateTime.now());
        goodsInfo.setUpdateTime(LocalDateTime.now());

        save(goodsInfo);

        List<GoodsAttr> goodsAttrs = goodsInfoParam.getGoodsAttrParams().stream()
                .map(e->{
                    GoodsAttr goodsAttr = new GoodsAttr();
                    BeanUtils.copyProperties(e,goodsAttr);
                    goodsAttr.setCreateTime(LocalDateTime.now());
                    goodsAttr.setGoodsId(goodsInfo.getId());

                    return goodsAttr;
                }).collect(Collectors.toList());
        goodsAttrService.saveBatch(goodsAttrs);

        return Boolean.TRUE;
    }

    @Override
    public GoodsDetailDTO goodsDetail(Long id) {

        GoodsInfo goodsInfo = getById(id);

        List<SkuInfo> skuInfos = skuInfoService.list(new LambdaQueryWrapper<SkuInfo>()
                .eq(SkuInfo::getGoodsId,id));

        GoodsDetailDTO goodsDetailDTO = new GoodsDetailDTO();
        if(goodsInfo != null){
            BeanUtils.copyProperties(goodsInfo,goodsDetailDTO);
        }
        List<SkuInfoDTO> skuInfoDTOS = skuInfos.stream().map(e->{
            SkuInfoDTO skuInfoDTO = new SkuInfoDTO();
            BeanUtils.copyProperties(e,skuInfoDTO);

            return skuInfoDTO;
        }).collect(Collectors.toList());

        goodsDetailDTO.setSkuInfoDTOS(skuInfoDTOS);

        return goodsDetailDTO;
    }

    @Override
    public String goodsAttrs(Long id) {
       List<GoodsAttr> goodsAttrs = goodsAttrService.list(new LambdaQueryWrapper<GoodsAttr>()
               .eq(GoodsAttr::getGoodsId,id));

       Map<String, List<GoodsAttr>> map = goodsAttrs.stream()
               .sorted(Comparator.comparing(GoodsAttr::getAttrSort))
               .collect(Collectors.groupingBy(GoodsAttr::getAttrName));

       HashMap<String,List<String>> result = new HashMap<>();
       map.entrySet().forEach(e->{
           String attrName = e.getKey();
           List<String> list = e.getValue().stream().map(x->{
               return x.getAttrValue();
           }).collect(Collectors.toList());
           result.put(attrName,list);
       });
       return JSONObject.toJSONString(result);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteGoods(List<Long> ids) {

        removeByIds(ids);
        //删除商品属性
        goodsAttrService.deleteGoodsAttrByGoodsId(ids);
        //删除商品SKU
        skuInfoService.deleteSKUByGoodsId(ids);

        return Boolean.TRUE;
    }
}
