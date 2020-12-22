package com.hjb.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(value = "test")
public class LoginController {

    @Value("${auth.get_token}")
    private String getTokneUrl;

    @RequestMapping(value = "/login", method = RequestMethod.POST)

    public Object login(@RequestParam("username") String username,
                        @RequestParam("password") String password){

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("username",username);
        hashMap.put("password",password);
        hashMap.put("grant_type","password");

        String post = HttpUtil.post(getTokneUrl, JSONObject.toJSONString(hashMap));

        return post;
    }
}
