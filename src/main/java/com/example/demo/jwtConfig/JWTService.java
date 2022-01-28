package com.example.demo.jwtConfig;


import com.example.demo.pojo.AuthRequest;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;


@Service
public class JWTService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public String generateToken(AuthRequest authRequest){
        System.out.println(authRequest.getUsername());
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword());
        authentication = authenticationManager.authenticate(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return jwtTokenUtil.generateToken(userDetails);
    }

    public Map<String, Object> parseToken(String token) {


        Claims claims = jwtTokenUtil.getAllClaimsFromToken(token);

        return claims.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


}
