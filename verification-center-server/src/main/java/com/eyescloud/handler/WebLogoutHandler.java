package com.eyescloud.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class WebLogoutHandler implements LogoutHandler {

    private Logger logger = LoggerFactory.getLogger(WebLogoutHandler.class);

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {

        logger.info("开始执行退出逻辑");
        String accessToken = httpServletRequest.getHeader("Authorization");
        accessToken = accessToken.replace("Bearer","").trim();
        if (accessToken != null){
            consumerTokenServices.revokeToken(accessToken);
        }
        logger.info("退出登入执行成功");
    }
}
