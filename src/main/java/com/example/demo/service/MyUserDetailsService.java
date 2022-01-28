package com.example.demo.service;

import com.example.demo.dao.UserInfoDao;
import com.example.demo.jwtConfig.JwtTokenUtil;
import com.example.demo.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserInfo dbUserInfo = userInfoDao.getUserByName(s);
        if(dbUserInfo == null){
            throw new UsernameNotFoundException("此帳號不存在");
        }
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_"+dbUserInfo.getAccess());
        System.out.println(auths.toString());
        return new User(dbUserInfo.getName(),dbUserInfo.getPassword(),auths);
    }

    public ResponseEntity<?> getToken(UserInfo authRequest) {
        System.out.println(authRequest);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));
            UserDetails user = loadUserByUsername(authRequest.getName());
//            String token = jwtUtil.generateToken(user);
            String token = "jwtUtil.generateToken(user)";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            System.out.println(token);
            return ResponseEntity.ok().headers(headers).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
