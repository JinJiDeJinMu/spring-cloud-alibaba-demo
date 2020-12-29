package com.hjb.handle;

import com.hjb.domain.Coupon;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ZheKouServiceImpl implements TemplateService{
    @Override
    public BigDecimal handler(BigDecimal money, Coupon coupon) {
        return money.multiply(coupon.getAmount());
    }
}
