package com.hjb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hjb.domain.po.ReceiveAddress;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinmu
 * @since 2020-11-30
 */
public interface ReceiveAddressService extends IService<ReceiveAddress> {

    List<ReceiveAddress> getUserAddress(Long userId);

}
