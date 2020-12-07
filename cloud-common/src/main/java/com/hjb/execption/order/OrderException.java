package com.hjb.execption.order;

import com.hjb.execption.BaseException;

public class OrderException extends BaseException {

    public OrderException(String message, Integer code){
        super(message,code);
    }
}
