package com.example.demo.config;

import com.example.demo.loginConfig.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;
import java.util.Map;

@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
// securedEnabled 可以控制API需要那些角色才能拜訪 ;
// prePostEnabled 適合進入方法前的權限驗證
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

        http.exceptionHandling()
                // 後面可接著設定 authenticationEntryPoint、accessDeniedHandler 把它換成我們自訂義的
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .accessDeniedHandler(new AccessDeniedHandlerImpl())
                .and()
                .addFilterAt(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                //新增 filter，也就是把我們的 loginAuthenticationFilter 跟原始的 UsernamePasswordAuthenticationFilter 去做替換，才能實現我們想要的功能。
                .authorizeRequests()
                .antMatchers("/api/ROOT/**").hasRole("ROOT")
                .antMatchers("/api/USER/**").hasRole("USER")
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .invalidateHttpSession(true)
                .logoutSuccessHandler((req, resp, auth) -> {
                    resp.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = resp.getWriter();
                    resp.setStatus(200);
                    Map<String, String> result = Map.of("message", "登出成功");
                    ObjectMapper om = new ObjectMapper();
                    out.write(om.writeValueAsString(result));
                    out.flush();
                    out.close();
                })
                .and()
                .csrf()
                .disable();
    }

    @Bean
    LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
        LoginAuthenticationFilter filter = new LoginAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandlerImpl());
        filter.setAuthenticationFailureHandler(new AuthenticationFailureHandlerImpl());
        filter.setFilterProcessesUrl("/api/login");
        return filter;
        /*
        * 建立 LoginAuthenticationFilter 的 bean，讓 Spring Security 可以採用，
        * 而在裡面就是建立的 LoginAuthenticationFilter 物件，並且設定成功及失敗的 handler，就是加我們剛剛新增那兩個 handler。
        * 最後最重要的是我們要設定該 filter 是專門 for 哪個路由，
        * 也就是這邊我們可以自定義 login 的路由，
        * 而不是採用傳統的 Spring Security 提供的表單路由。
        * */
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
