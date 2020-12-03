package com.hjb.execption;

import lombok.Data;
import org.apache.http.HttpStatus;

@Data
public abstract class BaseException extends RuntimeException{

    /**
     * 保存异常信息
     */
    private String message;

    /**
     * 保存响应状态码
     */
    private Integer code = HttpStatus.SC_INTERNAL_SERVER_ERROR;

    public BaseException(String message){
        super(message);
        this.message = message;
    }

    public BaseException(String message, Integer code){
        super(message);
        this.message = message;
        this.code = code;
    }
}
