package com.hjb.handle;

import com.hjb.domain.Coupon;

import java.math.BigDecimal;

public interface TemplateService {

    BigDecimal handler(BigDecimal money, Coupon coupon);
}
