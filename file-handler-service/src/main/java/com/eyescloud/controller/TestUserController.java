package com.eyescloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eyescloud.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestUserController {



    @RequestMapping(value = "/test" , produces = "application/json")
    public String test(@AuthenticationPrincipal OAuth2Authentication user) {

        try {

            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(user.getPrincipal()));
            User user1 = jsonObject.toJavaObject(User.class);
            System.out.println(user1);
        }catch (Exception e){
            e.printStackTrace();
        }

        return JSON.toJSONString(user.getPrincipal());
    }



}
