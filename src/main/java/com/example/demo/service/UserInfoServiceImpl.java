package com.example.demo.service;

import com.example.demo.AuthData;
import com.example.demo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class UserInfoServiceImpl implements UserInfoService{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthData login(UserInfo userInfo) {
        String userName = userInfo.getName();
        String userPassword = userInfo.getPassword();
        System.out.println(userName+userPassword);
        authenticate(userName,userPassword);
        System.out.println("HI");

        return null;
    }

    private void authenticate(String userName,String userPassword){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        // 使用 new UsernamePasswordAuthenticationToken(name,pw) 會實作 UserDetailService 裡面的 loadUserByUsername(name)
        // 此處詳見SpringUserService
        // 同時也會
    }
}
