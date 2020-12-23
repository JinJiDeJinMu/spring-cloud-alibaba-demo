package com.hjb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjb.domain.po.ReceiveAddress;
import com.hjb.mapper.ReceiveAddressMapper;
import com.hjb.service.ReceiveAddressService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jinmu
 * @since 2020-11-30
 */
@Service
public class ReceiveAddressServiceImpl extends ServiceImpl<ReceiveAddressMapper, ReceiveAddress> implements ReceiveAddressService {

    @Override
    public List<ReceiveAddress> getUserAddress(Long userId) {
        List<ReceiveAddress> addressList = list(new LambdaQueryWrapper<ReceiveAddress>()
        .eq(ReceiveAddress::getUserId,userId));

        return addressList;
    }
}
