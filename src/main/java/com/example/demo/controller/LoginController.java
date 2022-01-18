package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @GetMapping("/hasRootAuthority")
    public String hasRootAuthority(){
        return "ROOT access only!";
    }

    @GetMapping("/hasRootAndUserAuthority")
    public String hasRootAndUserAuthority(){
        return "ROOT & USER access only!";
    }
}
