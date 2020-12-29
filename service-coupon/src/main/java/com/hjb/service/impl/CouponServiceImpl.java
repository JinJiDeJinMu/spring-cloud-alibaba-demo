package com.hjb.service.impl;

import com.alibaba.nacos.common.utils.UuidUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hjb.domain.Coupon;
import com.hjb.domain.SkuInfo;
import com.hjb.domain.UserCoupon;
import com.hjb.domain.dto.UserCouponDTO;
import com.hjb.domain.param.CouponParam;
import com.hjb.feign.GoodsFeignService;
import com.hjb.mapper.CouponMapper;
import com.hjb.service.CouponService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjb.service.UserCouponService;
import com.hjb.util.SecurityUserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jinmu
 * @since 2020-12-26
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements CouponService {

    @Autowired
    private SecurityUserUtils securityUserUtils;

    @Autowired
    private GoodsFeignService goodsFeignService;

    @Autowired
    private UserCouponService userCouponService;

    @Override
    public boolean createdCoupon(CouponParam couponParam) {

        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(couponParam,coupon);

        coupon.setStatus(0);
        coupon.setCode(UuidUtils.generateUuid());
        coupon.setCreatedTime(new Date());
        coupon.setCreatedUserId(Long.valueOf(securityUserUtils.getUserInfo().get("id").toString()));
        coupon.setCreatedUserName(securityUserUtils.getUserInfo().get("username").toString());

        this.save(coupon);

        return true;
    }

    @Override
    public boolean stopCoupon(Long id) {
        Coupon coupon = this.getById(id);

        if(coupon == null){
            throw new RuntimeException("优惠券不存在");
        }
        coupon.setStatus(1);
        save(coupon);

        return true;
    }

    @Override
    public List<UserCouponDTO> getAvailable(Long skuId) {

        SkuInfo skuInfo = goodsFeignService.getSkuInfoById(skuId);

        Long userId = Long.valueOf(securityUserUtils.getUserInfo().get("id").toString());

        List<UserCoupon> list = userCouponService.list(new LambdaQueryWrapper<UserCoupon>()
                .eq(UserCoupon::getUserId, userId)
                .eq(UserCoupon::getStatus, 0));


        return null;
    }
}
