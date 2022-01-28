package com.example.demo.controller;

import com.example.demo.jwtConfig.JWTService;
import com.example.demo.jwtConfig.JwtTokenUtil;
import com.example.demo.pojo.AuthRequest;
import com.example.demo.pojo.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    StudentService studentService;
    @Autowired
    private JWTService jwtService;



    @GetMapping("/USER/getStudentList")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
//    @PostAuthorize("hasAnyAuthority('ROLE_USER')")
//    PreAuthorize 方法執行前驗證 / @PostAuthorize 方法執行後驗證
//    @PreFilter()
//    @PostFilter("filterObject.name=='Usong'")
//    @PreFilter傳入方法資料過濾 / @PostFilter方法回傳資料過濾
    public List<Student> getStudentList(){
        return studentService.getStudentList();
    }

    @PostMapping("/ROOT/createStudent")
    @Secured({"ROLE_ROOT"})
    public String createStudent(@RequestBody List<Student> studentList){
        return studentService.insertStudentList(studentList);
    }

    @PostMapping("/getToken")
    public ResponseEntity<Map<String, String>> issueToken(@Valid @RequestBody AuthRequest request) {
//        System.out.println(jwtTokenUtil.generateToken(request));

        String token = jwtService.generateToken(request);
        Map<String, String> response = Collections.singletonMap("token", token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/parse")
    public ResponseEntity<Map<String, Object>> parseToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        Map<String, Object> response = jwtService.parseToken(token);

        return ResponseEntity.ok(response);
//        return null;
    }
}
