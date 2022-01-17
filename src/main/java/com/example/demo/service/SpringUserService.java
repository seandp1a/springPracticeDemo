package com.example.demo.service;

import com.example.demo.USER_ACCESSES_TABLE;
import com.example.demo.UserInfo;
import com.example.demo.dao.UserInfoDao;
import com.example.demo.dao.UserInfoDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class SpringUserService implements UserDetailsService {
    @Autowired
    private UserInfoDaoImpl userInfoDao;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 此處覆寫loadUserByUsername，告訴程式你要去哪個資料庫哪張表取得真正的帳號密碼
        // 將密碼用BCryptPasswordEncoder加密，因為Spring Security會對使用者輸入的密碼做一樣的事情
        // 最後return 新的User去給Spring Security去做驗證
        System.out.println("loadUserByUserName(s) => s="+s);
        UserInfo userInfo = userInfoDao.getUserByName(s);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userInfo.getPassword());
        System.out.println(encodedPassword);
        return new User(userInfo.getName(),encodedPassword,getAuthorities(userInfo.getAccesses()));

//        return new User(userInfo.getName(),userInfo.getPassword(),getAuthorities(userInfo.getAccesses()));
//        若不使用encodedPassword，WebSecurityConiguration也不能做加密處裡
    }

    public Collection<GrantedAuthority> getAuthorities(String  accesses){
        int roleNum = USER_ACCESSES_TABLE.values().length;
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(roleNum);

        for(USER_ACCESSES_TABLE user : USER_ACCESSES_TABLE.values()){
            // user => 1 , user.name() => ROLE_ROOT
            if(user.name().equals("ROLE_"+accesses)){
                authList.add(new SimpleGrantedAuthority(user.name()));
            }
        }
        return authList;
    }

}
