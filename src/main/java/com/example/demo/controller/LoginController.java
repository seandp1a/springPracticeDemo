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




    @RequestMapping("/home")
    public String home(Model model){
        return "home";
    }
}
