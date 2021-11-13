package com.eyescloud.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebLoginFailureHandler implements AuthenticationFailureHandler  {

    @Autowired
    private ObjectMapper objectMapper;



    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        String msg = null;

        if(e instanceof BadCredentialsException){
            msg = "账号密码错误" ;
        } else {
            msg = e.getMessage();
        }
        httpServletResponse.setStatus(500);
        System.out.println("登入错误逻辑方法执行");
        ResponseUtil.addVaryFieldName(httpServletResponse , msg);
    }
}
