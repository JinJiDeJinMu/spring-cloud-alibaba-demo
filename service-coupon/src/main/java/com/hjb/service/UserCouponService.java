package com.hjb.service;

import com.hjb.domain.Coupon;
import com.hjb.domain.UserCoupon;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hjb.domain.dto.UserCouponDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinmu
 * @since 2020-12-26
 */
public interface UserCouponService extends IService<UserCoupon> {

    List<UserCouponDTO> getUserCoupon(Long userId, Integer status);

    boolean checkCoupon(UserCoupon userCoupon, Coupon coupon);

}
