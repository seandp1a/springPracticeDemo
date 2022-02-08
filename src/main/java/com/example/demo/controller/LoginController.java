package com.example.demo.controller;

import com.example.demo.pojo.AuthData;
import com.example.demo.pojo.UserInfo;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;

    @GetMapping("/hasRootAuthority")
    @ResponseBody()
    @Secured({"ROLE_ROOT"})
    public String hasRootAuthority(){
        return "ROOT access only!";
    }

    @GetMapping("/hasRootAndUserAuthority")
    @ResponseBody()
    @Secured({"ROLE_ROOT","ROLE_USER"})
    public String hasRootAndUserAuthority(){
        return "ROOT & USER access only!";
    }


    @RequestMapping("/home")
    public String home(Model model){
        return "home";
    }
}
