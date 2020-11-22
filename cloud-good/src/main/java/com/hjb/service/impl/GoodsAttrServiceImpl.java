package com.hjb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjb.domain.po.GoodsAttr;
import com.hjb.mapper.GoodsAttrMapper;
import com.hjb.service.GoodsAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jinmu
 * @since 2020-11-20
 */
@Service
public class GoodsAttrServiceImpl extends ServiceImpl<GoodsAttrMapper, GoodsAttr> implements GoodsAttrService {

    @Autowired
    private GoodsAttrMapper goodsAttrMapper;

    @Override
    public Boolean deleteGoodsAttrByGoodsId(List<Long> goodsIds) {

        return goodsAttrMapper.deleteGoodsAttrByGoodsId(goodsIds);
    }
}
