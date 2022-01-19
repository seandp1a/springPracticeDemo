package com.example.demo.controller;

import com.example.demo.pojo.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    StudentService studentService;

    @GetMapping("/USER/getStudentList")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
//    @PostAuthorize("hasAnyAuthority('ROLE_USER')")
//    PreAuthorize 方法執行前驗證 / @PostAuthorize 方法執行後驗證
    //    @PreFilter()
    @PostFilter("filterObject.name=='Usong'")
//     @PreFilter傳入方法資料過濾 / @PostFilter方法回傳資料過濾
    public List<Student> getStudentList(){
        System.out.println("read....");
        return studentService.getStudentList();
    }

    @PostMapping("/ROOT/createStudent")
    @Secured({"ROLE_ROOT"})
    public String createStudent(@RequestBody List<Student> studentList){
        return studentService.insertStudentList(studentList);
    }
}
