package com.hjb.service;

import com.hjb.domain.Coupon;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hjb.domain.dto.UserCouponDTO;
import com.hjb.domain.param.CouponParam;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinmu
 * @since 2020-12-26
 */
public interface CouponService extends IService<Coupon> {

    boolean createdCoupon(CouponParam couponParam);

    boolean stopCoupon(Long id);

    List<UserCouponDTO> getAvailable(Long skuId);
}
