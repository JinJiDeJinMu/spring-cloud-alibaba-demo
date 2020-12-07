package com.hjb.domain.param;

import com.hjb.domain.po.GoodsInfo;
import com.hjb.domain.po.ReceiveAddress;
import com.hjb.domain.po.SkuInfo;
import com.hjb.domain.po.User;
import lombok.Data;

@Data
public class OrderTrade {

    private User user;

    private GoodsInfo goodsInfo;

    private SkuInfo skuInfo;

    private ReceiveAddress address;
}
