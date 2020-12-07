package com.hjb.constant;

public class CommonConstants {
    // 用户token异常
    public static final Integer EX_USER_INVALID_CODE = 5000;
    public static final Integer EX_USER_PASS_INVALID_CODE = 50001;
    public static final Integer EX_USER_JWT_IS_VERIFIER = 50002; //Token 无效
    public static final Integer EX_USER_JWT_EXPIRATION_TIME = 50003;//Token 失效


    //order订单相关
    public static final Integer ORDER_CHECK_UN_RIGHT = 6000;//订单不合法
    public static final Integer ORDER_SKU_MOUNT = 6001;//库存不足
    public static final Integer ORDER_SUBMIT = 6002;//订单提交失败
}
