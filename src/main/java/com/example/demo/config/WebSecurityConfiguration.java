package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    /*
    * Security 預設規定所有 API，都要先通過身份驗證才可存取。即使在匯入完成後，什麼程式都沒寫也會生效，除非被覆寫。
    * 然而我們還沒有建立好授權機制，若使用 Postman 之類的工具發送請求，一律會收到 HTTP 401（Unauthorized）的狀態碼。
    * */
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected  void  configure(HttpSecurity http) throws Exception{
        /*
        * 此處設定API
        * 如拜訪權限、CSRF等設定
        * */
        http
                .authorizeRequests()
//                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers(HttpMethod.GET,"/students").permitAll()
                .antMatchers(HttpMethod.GET,"/students/?*").authenticated()
                .antMatchers(HttpMethod.POST).permitAll()
                .antMatchers(HttpMethod.DELETE).permitAll()
                .antMatchers(HttpMethod.PUT).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        // Spring Security 在身分驗證這塊會將使用者輸入的密碼進行BCryptPasswordEncoder加密
        // 目的是用來跟資料庫密碼加密後做比對驗證是否一樣
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
//              .passwordEncoder(passwordEncoder);
//              若此處不用BCrypt加密，loadUserByUsername回傳的密碼也得是沒加密過的

    }

    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}
