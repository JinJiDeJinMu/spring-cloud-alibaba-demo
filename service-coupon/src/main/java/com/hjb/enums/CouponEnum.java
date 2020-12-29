package com.hjb.enums;

import com.hjb.handle.LiJianServiceImpl;
import com.hjb.handle.ManJianServiceImpl;
import com.hjb.handle.TemplateService;
import com.hjb.handle.ZheKouServiceImpl;


public enum CouponEnum {

    MANJIAN_TEM(0,new ManJianServiceImpl()),
    ZHEKOU_TEM(1,new ZheKouServiceImpl()),
    LIJIAN_TEM(2,new LiJianServiceImpl());

    private Integer type;

    private TemplateService templateService;

    CouponEnum(Integer type, TemplateService templateService) {
        this.templateService = templateService;
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public TemplateService getTemplateService() {
        return templateService;
    }

    public void setTemplateService(TemplateService templateService) {
        this.templateService = templateService;
    }

    public TemplateService getServiceByType(Integer type){
        for(CouponEnum couponEnum : CouponEnum.values()){
            if(type == couponEnum.getType()){
                return couponEnum.getTemplateService();
            }
        }
        return null;
    }
}
