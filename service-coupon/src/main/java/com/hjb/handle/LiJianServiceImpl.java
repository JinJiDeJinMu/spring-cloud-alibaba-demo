package com.hjb.handle;

import com.hjb.domain.Coupon;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LiJianServiceImpl implements TemplateService{
    @Override
    public BigDecimal handler(BigDecimal money, Coupon coupon) {
        return money.compareTo(coupon.getAmount()) <=0 ? new BigDecimal(0): money.subtract(coupon.getAmount());
    }
}
