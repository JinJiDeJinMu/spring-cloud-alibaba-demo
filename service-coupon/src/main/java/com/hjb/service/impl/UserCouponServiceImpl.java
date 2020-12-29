package com.hjb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hjb.domain.Coupon;
import com.hjb.domain.UserCoupon;
import com.hjb.domain.dto.UserCouponDTO;
import com.hjb.mapper.UserCouponMapper;
import com.hjb.service.CouponService;
import com.hjb.service.UserCouponService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jinmu
 * @since 2020-12-26
 */
@Service
public class UserCouponServiceImpl extends ServiceImpl<UserCouponMapper, UserCoupon> implements UserCouponService {

    @Autowired
    private CouponService couponService;

    @Transactional
    @Override
    public List<UserCouponDTO> getUserCoupon(Long userId, Integer status) {

        List<UserCoupon> list = this.list(new LambdaQueryWrapper<UserCoupon>()
                .eq(UserCoupon::getUserId, userId)
                .eq(UserCoupon::getStatus,status)
                .orderByDesc(UserCoupon::getCreatedTime));

        List<UserCouponDTO> result = list.stream().map(e -> {
            Coupon coupon = couponService.getById(e.getCouponId());
            UserCouponDTO userCouponDTO = new UserCouponDTO();

            if (e.getStatus() == 0) {
                //判断优惠卷是否失效
                if (!checkCoupon(e, coupon)) {
                    e.setStatus(2);
                    userCouponDTO.setStatus(2);
                    this.save(e);
                }
            }
            BeanUtils.copyProperties(coupon, userCouponDTO);
            return userCouponDTO;
        }).collect(Collectors.toList());

        return result;
    }

    @Override
    public boolean checkCoupon(UserCoupon userCoupon, Coupon coupon) {
        Date now = new Date();
        Integer timeType = coupon.getTimeType();
        if(timeType == 0){
            if(now.after(coupon.getEndTime())){
                return false;
            }
        }else if(timeType == 1){
            Date receiveTime = userCoupon.getReceiveTime();
            Calendar time = Calendar.getInstance();
            time.setTime(receiveTime);
            time.set(Calendar.DATE, time.get(Calendar.DATE) + coupon.getTimeDay());
            if(now.after(time.getTime())){
                return false;
            }
        }
        return true;
    }
}
