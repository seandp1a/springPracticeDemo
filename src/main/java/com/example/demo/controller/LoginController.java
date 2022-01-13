package com.example.demo.controller;

import com.example.demo.UserInfo;
import com.example.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private  UserInfoService userInfoService;

    @PostMapping("/login")
    public String login(@RequestBody UserInfo loginData){
//        System.out.println(loginData);
//        return "登入成功";

        UserInfo userInfo = new UserInfo();
        userInfo.setName(loginData.getName());
        userInfo.setPassword(loginData.getPassword());
        userInfoService.login(userInfo);
        return "登入成功";
    }
}
