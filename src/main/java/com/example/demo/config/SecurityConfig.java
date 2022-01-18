package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // userDetailsService 自定義街口
       auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http.formLogin() //自定義編寫的登入頁面
                .loginPage("/login.html") // 登入頁面設置
                .loginProcessingUrl("/user/login") // 登入訪問路徑
                .defaultSuccessUrl("/students").permitAll() // 登入後導向路徑
                .and().authorizeRequests()
                .antMatchers(HttpMethod.GET,"/hasRootAuthority").hasAuthority("ROLE_ROOT") // 可不需ROLE_前綴
                .antMatchers(HttpMethod.GET,"/hasRootAndUSERAuthority").hasAnyAuthority("ROLE_ROOT,ROLE_USER")
                .antMatchers(HttpMethod.GET,"/hasRole").hasRole("ROLE_USER") // hasRole 內的字串一定要有ROLE_前綴
                .antMatchers(HttpMethod.GET,"/hasAnyRole").hasRole("ROLE_ROOT,ROLE_USER")
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}
