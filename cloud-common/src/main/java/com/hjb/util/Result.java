package com.hjb.util;

import lombok.Data;

@Data
public class Result {

    // 响应业务状态
    private Integer code;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;


    public static Result build(Integer code, String msg, Object data) {
        return new Result(code, msg, data);
    }


    public static Result SUCCESS(Object data) {
        return new Result(data);
    }

    public static Result SUCCESS() {
        return new Result(null);
    }

    public static Result FAILURE(String msg) {
        return new Result(500, msg, null);
    }

    public static Result FAILURE(Object data) {
        return new Result(500, "error", data);
    }

    public Result() {

    }

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(Object data) {
        this.code = 200;
        this.msg = "OK";
        this.data = data;
    }
}
