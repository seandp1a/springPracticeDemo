package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
    * Security 預設規定所有 API，都要先通過身份驗證才可存取。即使在匯入完成後，什麼程式都沒寫也會生效，除非被覆寫。
    * 然而我們還沒有建立好授權機制，若使用 Postman 之類的工具發送請求，一律會收到 HTTP 401（Unauthorized）的狀態碼。
    */
    @Autowired
    private DataSource dataSource = null;

    @Value("$(system.user.password.secret)")
    private String secret = null;

    String pwdQuery = "select name ,password,access from user where name = ?";
    String roleQuery = "select user.name,role.name from user ,role , user_role " +
            "where user.id = user_role.user_id and role.id = user_role.role_id " +
            "and user.name = ?";

    /**
     * 用來設定用戶登入服務，主要是user-detail機制，可以給予用戶賦予角色
     * @param auth 簽名管理器構造器，用於構建用戶具體權限控制
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(this.secret);
        System.out.println(new BCryptPasswordEncoder().encode("123123"));
        auth.jdbcAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery(pwdQuery)
                .authoritiesByUsernameQuery(roleQuery);
    }

    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder2() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    /**
     * 用來配置Filter鍊
    */
    public void configure(WebSecurity web){

    }

    /**
     * 此處對Request的攔截做設定
     * 如拜訪權限、驗證需求、CSRF等設定
     */
    protected  void  configure(HttpSecurity http) throws Exception{
        http.formLogin();
//        http
//                .authorizeRequests()
////                .antMatchers(HttpMethod.GET).permitAll()
//                .antMatchers(HttpMethod.GET,"/students").permitAll()
//                .antMatchers(HttpMethod.GET,"/students/?*").authenticated()
//                .antMatchers(HttpMethod.POST).permitAll()
//                .antMatchers(HttpMethod.DELETE).permitAll()
//                .antMatchers(HttpMethod.PUT).permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .csrf().disable();
    }


}
