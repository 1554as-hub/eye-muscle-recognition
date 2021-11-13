package com.eyescloud.controller;

import com.eyescloud.entity.User;
import com.eyescloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @GetMapping(value = "/userInfo" , produces = "application/json")
    public ResponseEntity<User> getUserInfo(@AuthenticationPrincipal User user) {
        User user1 = userService.getById(user.getId());
        return ResponseEntity.status(200).body(user1);
    }

}
