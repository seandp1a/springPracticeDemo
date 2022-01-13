package com.example.demo.service;

import com.example.demo.AuthData;
import com.example.demo.UserInfo;

public interface UserInfoService {
    AuthData login(UserInfo userInfo);
}
