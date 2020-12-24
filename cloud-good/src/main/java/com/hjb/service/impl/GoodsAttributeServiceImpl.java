package com.hjb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjb.domain.GoodsAttribute;
import com.hjb.mapper.GoodsAttributeMapper;
import com.hjb.service.GoodsAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jinmu
 * @since 2020-11-25
 */
@Service
public class GoodsAttributeServiceImpl extends ServiceImpl<GoodsAttributeMapper, GoodsAttribute> implements GoodsAttributeService {

    @Autowired
    private GoodsAttributeMapper goodsAttributeMapper;

    @Override
    public Boolean deleteGoodsAttrByGoodsId(List<Long> goodsIds) {
        return goodsAttributeMapper.deleteGoodsAttrByGoodsId(goodsIds);
    }
}
