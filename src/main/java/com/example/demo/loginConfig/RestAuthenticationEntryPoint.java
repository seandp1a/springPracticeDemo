package com.example.demo.loginConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Map;


public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint , Serializable {
    private static final long serialVersionUID = -7858869558953243875L;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = Map.of("error", "請先登入才能進行此操作");
        String error = mapper.writeValueAsString(map);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setStatus(httpServletResponse.SC_UNAUTHORIZED);
//        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        PrintWriter writer = httpServletResponse.getWriter();
        writer.write(error);
        writer.flush();
        writer.close();
    }
    /*
     * 這個 AuthenticationEntryPoint 的 Class 主要是用來設定使用者的權限進入點，
     * 也就是說使用者要使用，一定要先經過登入審核的動作
     * ，因此在這邊我們就可以做個手腳，不採用傳統 Spring Security 的 Login 頁面，
     * 透過 Override commerce 這個 method，去做設定，讓它可以直接回覆 json 格式。
     * 這邊還有一個好處，也是前端不必再特別在每個頁面 call api 去詢問後端，是否這個人處在登入狀態。
     * 因為這邊後端完全都擋好了，前端只需要判斷每隻 API 回傳 401 status 的可能性就可以了。
     * 只要出現 401 即可導入到登入頁面。*/
}
