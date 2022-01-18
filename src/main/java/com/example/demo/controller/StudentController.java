package com.example.demo.controller;

import com.example.demo.pojo.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private StudentService studentService;

    @PostMapping("/students")
    public String insert(@RequestBody Student student) {
        return studentService.insertStudent(student);
    }

    @PostMapping("/students/batch")
    public String insertList(@RequestBody List<Student> studentList){
       return studentService.insertStudentList(studentList);
    }

    @GetMapping("/students/{studentId}")
    public Student selectOneStudent(@PathVariable Integer studentId){
        return studentService.getDetailById(studentId);
    }

    @GetMapping("/students")
    public List<Student> select(){
        return studentService.getStudentList();
    }



    @DeleteMapping("/students/{studentId}")
    public String delete(@PathVariable Integer studentId){
       return studentService.deleteStudent(studentId);
    }

    @PutMapping("/students/{studentId}")
    public String update(@RequestBody Student student,
                         @PathVariable Integer studentId){
        return studentService.updateStudent(studentId,student);
    }
}
