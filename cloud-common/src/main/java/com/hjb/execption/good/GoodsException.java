package com.hjb.execption.good;

import com.hjb.execption.BaseException;

public class GoodsException extends BaseException {

    public GoodsException(String message, Integer code){
        super(message,code);
    }
}
