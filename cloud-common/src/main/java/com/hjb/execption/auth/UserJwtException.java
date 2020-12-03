package com.hjb.execption.auth;

import com.hjb.constant.CommonConstants;
import com.hjb.execption.BaseException;

public class UserJwtException extends BaseException {

    public UserJwtException(String message){
        super(message, CommonConstants.EX_USER_INVALID_CODE);
    }
    public UserJwtException(String message,Integer code){
        super(message, code);
    }
}
