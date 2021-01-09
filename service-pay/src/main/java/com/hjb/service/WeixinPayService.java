package com.hjb.service;

import java.util.Map;

public interface WeixinPayService {

    /**
     * 创建支付二维码
     * @param out_order_sn
     * @param total_price
     * @return
     */
     Map createNative(String out_order_sn, String total_price);
}
