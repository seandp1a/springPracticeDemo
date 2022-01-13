package com.example.demo.service;

import com.example.demo.UserInfo;
import com.example.demo.dao.UserInfoDao;
import com.example.demo.dao.UserInfoDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SpringUserService implements UserDetailsService {
    @Autowired
    private UserInfoDaoImpl userInfoDao;



    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoDao.getUserByName(s);
        System.out.println("Spring UserService after userInfoDao: "+userInfo.getName());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userInfo.getPassword());
        return new User(userInfo.getName(),encodedPassword, Collections.emptyList());
    }


}
