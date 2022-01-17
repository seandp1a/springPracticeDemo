package com.example.demo.service;

import com.example.demo.AuthData;
import com.example.demo.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class UserInfoService {

    public AuthData login(UserInfo userInfo) {
        String userName = userInfo.getName();
        String userPassword = userInfo.getPassword();
        System.out.println(userName+userPassword);

        System.out.println("HI");

        return null;
    }


}
