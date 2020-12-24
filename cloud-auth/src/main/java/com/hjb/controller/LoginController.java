package com.hjb.controller;

import com.hjb.service.LoginService;
import com.hjb.util.AuthToken;
import com.hjb.util.CookieUtil;
import com.hjb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/au")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Value("${auth.clientId}")
    private String clientId;

    @Value("${auth.clientSecret}")
    private String clientSecret;

    private static final String GRAND_TYPE = "password";//授权模式 密码模式


    @Value("${auth.cookieDomain}")
    private String cookieDomain;

    //Cookie生命周期
    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password){
        //登录 之后生成令牌的数据返回
        AuthToken authToken = loginService.login(username, password, clientId, clientSecret, GRAND_TYPE);

        //设置到cookie中
        saveCookie(authToken.getAccessToken());
        return Result.SUCCESS(authToken.getAccessToken());
    }

    private void saveCookie(String token){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.addCookie(response,cookieDomain,"/","Authorization",token,cookieMaxAge,false);
    }

}
