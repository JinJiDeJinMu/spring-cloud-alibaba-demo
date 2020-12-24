package com.hjb.domain.param;

import com.hjb.domain.GoodsInfo;
import com.hjb.domain.ReceiveAddress;
import com.hjb.domain.SkuInfo;
import com.hjb.domain.User;
import lombok.Data;

@Data
public class OrderTrade {

    private User user;

    private GoodsInfo goodsInfo;

    private SkuInfo skuInfo;

    private ReceiveAddress address;
}
