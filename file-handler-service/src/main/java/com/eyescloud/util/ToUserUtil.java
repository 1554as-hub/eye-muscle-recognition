package com.eyescloud.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.eyescloud.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

@Slf4j
public class ToUserUtil {

    public User getUser(OAuth2Authentication user) {
        User user1 = null;
        try {
            log.info(user.getPrincipal().toString());
            JSONObject jsonObject = JSON.parseObject(JSONObject.toJSONString(user.getPrincipal()));
            user1 = jsonObject.toJavaObject(User.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return user1;
    }

}
