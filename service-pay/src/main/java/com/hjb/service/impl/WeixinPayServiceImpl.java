package com.hjb.service.impl;

import com.github.wxpay.sdk.WXPayUtil;
import com.hjb.service.WeixinPayService;
import com.hjb.util.WeixinPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

@Service
public class WeixinPayServiceImpl implements WeixinPayService {
    
    @Autowired
    private WeixinPayUtil weixinPayUtil;

    @Override
    public Map createNative(String out_order_sn, String total_price) {
        //封装参数
        String params = weixinPayUtil.toMap(out_order_sn, total_price);

        Map result = weixinPayUtil.getResult(params);

        Map<String,String> dataMap = new HashMap<String,String>();
        dataMap.put("code_url",result.get("code_url").toString());
        dataMap.put("out_trade_no",out_order_sn);
        dataMap.put("total_fee",total_price);

        return dataMap;
    }
}
