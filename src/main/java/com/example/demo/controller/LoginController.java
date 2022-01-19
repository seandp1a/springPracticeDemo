package com.example.demo.controller;

import com.example.demo.pojo.UserInfo;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @GetMapping("/hasRootAuthority")
    @Secured({"ROLE_ROOT"})
    public String hasRootAuthority(){
        return "ROOT access only!";
    }

    @GetMapping("/hasRootAndUserAuthority")
    @Secured({"ROLE_ROOT","ROLE_USER"})
    public String hasRootAndUserAuthority(){
        return "ROOT & USER access only!";
    }

}
