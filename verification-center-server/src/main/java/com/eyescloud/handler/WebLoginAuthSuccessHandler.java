package com.eyescloud.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebLoginAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final static Logger logger =  LoggerFactory.getLogger(WebLoginAuthSuccessHandler.class) ;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        System.out.println(" onAuthenticationSuccess 登入验证中");
        super.onAuthenticationSuccess(request , response , chain , authentication);
        System.out.println(" onAuthenticationSuccess 登入成功");

    }



}
