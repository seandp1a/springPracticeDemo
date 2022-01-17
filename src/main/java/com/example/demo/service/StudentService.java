package com.example.demo.service;

import com.example.demo.Student;

import java.util.List;

public interface StudentService {
    Student getDetailById(Integer studentId);

    List<Student> getStudentList();

    String insertStudent(Student student);

    String insertStudentList(List<Student> studentList);

    String deleteStudent(Integer studentId);

    String updateStudent(Integer studentId,Student student);
}
