package com.example.demo.controller;

import com.example.demo.jwtConfig.JWTService;
import com.example.demo.jwtConfig.JwtTokenUtil;
import com.example.demo.pojo.AuthRequest;
import com.example.demo.pojo.Student;
import com.example.demo.pojo.UserInfo;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class ApiController {
    @Autowired
    StudentService studentService;

    @GetMapping("/USER/getStudentList")
    public List<Student> getStudentList(){
        return studentService.getStudentList();
    }

    @PostMapping("/ROOT/createStudent")
    public String createStudent(@RequestBody List<Student> studentList){
        return studentService.insertStudentList(studentList);
    }

    @GetMapping("/hasRootAuthority")
    public String hasRootAuthority(){
        return "ROOT access only!";
    }

    @GetMapping("/hasRootAndUserAuthority")
    public String hasRootAndUserAuthority(){
        return "ROOT & USER access only!";
    }

    @GetMapping("/hasRootRole")
    public String hasRootRole(){
        return "ROOT access only!";
    }

    @GetMapping("/hasAnyRole")
    public String hasAnyRole(){
        return "ROOT & USER access only!";
    }

    @GetMapping("/getAuthenticatedUserInfo")
    public UserInfo getAuthenticatedUserInfo(){
        UserInfo result = new UserInfo();
        // Security會從sessionID取得登入資訊如角色、權限等
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        result.setName(authentication.getName());
        result.setAccess(authentication.getAuthorities().iterator().next().toString());
        return result;
    }

}
