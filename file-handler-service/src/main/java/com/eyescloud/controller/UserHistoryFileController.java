package com.eyescloud.controller;

import com.alibaba.fastjson.JSON;

import com.eyescloud.entity.FileModle;
import com.eyescloud.entity.User;
import com.eyescloud.service.FileHandlerService;
import com.eyescloud.util.ToUserUtil;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserHistoryFileController {

    private ToUserUtil userUtil = new ToUserUtil();

    @Autowired
    private FileHandlerService fileHandlerService;


//
//    @GetMapping(value = "/gethistoryfile" , produces = "application/json")
//    public ResponseEntity<String> getUserHistoryFile(OAuth2Authentication oAuth2Authentication
//            , @RequestParam(defaultValue = "1") Integer pageNum) {
//
//        User user = userUtil.getUser(oAuth2Authentication);
//        PageHelper.startPage(pageNum , 10) ;
//        List<FileModle> fileModles = fileHandlerService.getUserFileHistory(user.getId());
//        PageInfo<FileModle> pageInfo = new PageInfo<>(fileModles);
//        return ResponseEntity.ok().body(JSON.toJSONString(pageInfo));
//
//    }



}
