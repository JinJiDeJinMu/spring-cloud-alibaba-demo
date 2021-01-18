package com.hjb.domain.param;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class UserParam {

    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message = "密码不能为空")
    private String password;

    @NotNull(message = "手机号码不能为空")
    private String phone;

}
