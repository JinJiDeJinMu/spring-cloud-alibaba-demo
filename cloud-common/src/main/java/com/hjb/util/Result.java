package com.hjb.util;

import lombok.Data;

@Data
public class Result {

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    private String ok;    // 不使用

    public static Result build(Integer status, String msg, Object data) {
        return new Result(status, msg, data);
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


    public Result(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Result(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isSUCCESS() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getSUCCESS() {
        return ok;
    }

    public void setSUCCESS(String ok) {
        this.ok = ok;
    }

}
