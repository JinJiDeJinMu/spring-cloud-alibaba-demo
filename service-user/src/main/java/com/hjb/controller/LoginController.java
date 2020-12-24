package com.hjb.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hjb.annotation.IgnoreAuth;
import com.hjb.domain.param.UserParam;
import com.hjb.domain.User;
import com.hjb.execption.auth.UserJwtException;
import com.hjb.service.UserService;
import com.hjb.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * 登录注册接口
 * @author JinMu
 * @date 2020-12-01
 */
@RestController
@RequestMapping("/login")
@Api("用户登录")
public class LoginController {

    @Autowired
    private UserService userService;


    @ApiOperation("登录接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @IgnoreAuth
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password){

       /* User user = userService.getOne(new LambdaQueryWrapper<User>()
        .eq(User::getUserName,username));
        if(user == null){
            return Result.FAILURE("用户不存在");
        }
        if(!DigestUtil.bcryptCheck(password,user.getPassword())){
            return Result.FAILURE("用户密码不正确");
        }

        String token = JwtUtils.createdToken(user.getId(),username);*/

        return Result.SUCCESS();
    }

    @ApiOperation("注册接口")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @IgnoreAuth
    public Result register(@RequestBody UserParam userParam){

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
        user.setPassword(DigestUtil.bcrypt(userParam.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        user.setPhone(userParam.getPhone());
        user.setRegisterTime(LocalDateTime.now());

        userService.save(user);

        return Result.SUCCESS();
    }
    @GetMapping("test")
    public Result test(){
        throw new UserJwtException("测试异常信息");
    }

}
