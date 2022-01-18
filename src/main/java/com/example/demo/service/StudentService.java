package com.example.demo.service;

import com.example.demo.pojo.Student;
import com.example.demo.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentService {
    @Autowired
    private StudentDao studentDao;


    public Student getDetailById(Integer studentId) {
        return studentDao.getDetailById(studentId);
    }


    public List<Student> getStudentList() {
        return studentDao.getStudentList();
    }


    public String insertStudent(Student student) {
        return studentDao.insertStudent(student);
    }


    public String insertStudentList(List<Student> studentList) {
        return studentDao.insertStudentList(studentList);
    }


    public String deleteStudent(Integer studentId) {
        return studentDao.deleteStudent(studentId);
    }


    public String updateStudent(Integer studentId, Student student) {
        Student studentInfoInSQL =studentDao.getDetailById(studentId);
        if(studentInfoInSQL!=null){
            return studentDao.updateStudent(studentId,student);
        }else {
            return "update failed,cannot find the student!";
        }
    }
}
