package com.eyescloud.config;

import com.eyescloud.entity.User;
import com.eyescloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class DetailService implements UserDetailsService {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User userDetail = userService.getUserByUserName(s);
        if(userDetail == null){
            throw  new UsernameNotFoundException("用户不存在异常");
        }

        return userDetail;
    }
}
