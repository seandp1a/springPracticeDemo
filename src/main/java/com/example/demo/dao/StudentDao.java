package com.example.demo.dao;

import com.example.demo.Student;

import java.util.List;

public interface StudentDao {
    Student getDetailById(Integer studentId);

    List<Student> getStudentList();

    String insertStudent(Student student);

    String insertStudentList(List<Student> studentList);

    String deleteStudent(Integer studentId);

    String updateStudent(Integer studentId,Student student);
}
