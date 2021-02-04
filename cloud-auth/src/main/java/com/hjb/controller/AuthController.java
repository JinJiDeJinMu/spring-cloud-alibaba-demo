package com.hjb.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hjb.domain.User;
import com.hjb.domain.param.UserParam;
import com.hjb.service.LoginService;
import com.hjb.service.UserService;
import com.hjb.util.AuthToken;
import com.hjb.util.CookieUtil;
import com.hjb.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;


@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Value("${auth.clientId}")
    private String clientId;

    @Value("${auth.clientSecret}")
    private String clientSecret;

    private static final String GRAND_TYPE_PASSWORD = "password";

    private static final String GRAND_TYPE_REFRESH = "refresh_token";

    @Value("${auth.cookieDomain}")
    private String cookieDomain;

    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;

    private static String TOKEN = "Authorization";

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password){
        AuthToken authToken = loginService.login(username, password, clientId, clientSecret, GRAND_TYPE_PASSWORD);
        saveCookie(authToken.getAccessToken());
        return Result.SUCCESS(authToken);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public Result refresh(@RequestParam("refresh_token") String refresh_token){
        AuthToken authToken = loginService.refresh(refresh_token, clientId, clientSecret, GRAND_TYPE_REFRESH);
        saveCookie(authToken.getAccessToken());
        return Result.SUCCESS(authToken);
    }

    private void saveCookie(String token){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.addCookie(response,cookieDomain,"/",TOKEN,token,cookieMaxAge,false);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Result logout(){
        saveCookie(null);
        return Result.SUCCESS();
    }

    @ApiOperation("注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result register(@Valid UserParam userParam){

        User user = userService.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername,userParam.getUsername()));

        if(user != null){
            return Result.FAILURE("用户名已存在");
        }
        user = userService.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getPhone,userParam.getPhone()));
        if(user != null){
            return Result.FAILURE("手机号已被注册");
        }
        user = new User();
        user.setUsername(userParam.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userParam.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        user.setPhone(userParam.getPhone());
        user.setRegisterTime(LocalDateTime.now());

        userService.save(user);

        return Result.SUCCESS("注册成功");
    }

}
