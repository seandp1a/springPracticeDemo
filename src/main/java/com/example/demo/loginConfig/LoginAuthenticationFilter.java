package com.example.demo.loginConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE) ||
                request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;
            try (InputStream stream = request.getInputStream()) {
                Map<String, String> body = mapper.readValue(stream, Map.class);
                authRequest = new UsernamePasswordAuthenticationToken(
                        body.get("username"), body.get("password")
                );
            } catch (IOException e) {
                e.printStackTrace();
                authRequest = new UsernamePasswordAuthenticationToken("", "");
            } finally {
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        }
        else {
            return super.attemptAuthentication(request, response);
        }
    }
    /*
    * 這個 Filter 主要功用就是當使用者登入後，會經過的 Filter，
    * 它會去取得裡面的 request 的參數，因此在這邊我們可以更動的就是如何去取得 request 參數，
    * 因為改成 Restful 的形式，因此會先判斷 request 過來的形式是不是 json 的格式，
    * 接著取得 request 的輸入流，這個就是我們的 body 內容，因此可以將它轉成 Map 格式，在方便我們取得裡面的參數。
    * 也因此參數這邊我們可以取成 account、password，而不是傳統的 username、password，我們可以自定義。*/
}
