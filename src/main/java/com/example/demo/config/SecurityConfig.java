package com.example.demo.config;

import com.example.demo.jwtConfig.JWTAuthenticationFilter;
import com.example.demo.jwtConfig.JwtTokenUtil;
import com.example.demo.loginConfig.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;

import java.io.PrintWriter;
import java.util.Map;


@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // userDetailsService 自定義街口
       auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * 使用Spring Security 預設登入頁面 formLogin
         * 登入成功轉址到 index.html
         * API 拜訪權限在此設定
         * Server 的 Session 也可在此做控制
         * */
        http
                .logout().logoutUrl("/api/logout").logoutSuccessUrl("/login").permitAll();
        http
                .formLogin() // 啟用Spring Security預設登入頁面
                .defaultSuccessUrl("/#/") // 登入後導向路徑
                .and()
                .authorizeRequests() // 設置 request 拜訪權限及登入驗證
                .antMatchers(HttpMethod.GET,"/hasRootAuthority").hasAuthority("ROLE_ROOT") // 要ROLE_前墜
                .antMatchers(HttpMethod.GET,"/hasRootAndUSERAuthority").hasAnyAuthority("ROLE_ROOT,ROLE_USER")
                .antMatchers(HttpMethod.GET,"/hasRootRole").hasAnyRole("ROOT") // 不用ROLE_前墜
                .antMatchers(HttpMethod.GET,"/hasAnyRole").hasAnyRole("ROOT,USER")
                .antMatchers("/USER/**").hasAnyAuthority("ROLE_USER")
                .antMatchers("/ROOT/**").hasAnyAuthority("ROLE_ROOT")
                .anyRequest().authenticated() // 沒被設置的路徑.禁止訪問
                .and().csrf().disable();
        http
                .sessionManagement()
                .maximumSessions(2) // 某帳號同時最高上線數
                .maxSessionsPreventsLogin(false) // 某帳號已達最高上線數後，true->禁止下一個登入，false->踢掉前面的使用者
                .expiredUrl("/#/login")
                .and()
                .invalidSessionUrl("/#/login");

    }

}
