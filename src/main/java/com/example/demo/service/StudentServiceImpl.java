package com.example.demo.service;

import com.example.demo.Student;
import com.example.demo.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    public Student getDetailById(Integer studentId) {
        return studentDao.getDetailById(studentId);
    }

    @Override
    public List<Student> getStudentList() {
        return studentDao.getStudentList();
    }

    @Override
    public String insertStudent(Student student) {
        return studentDao.insertStudent(student);
    }

    @Override
    public String insertStudentList(List<Student> studentList) {
        return studentDao.insertStudentList(studentList);
    }

    @Override
    public String deleteStudent(Integer studentId) {
        return studentDao.deleteStudent(studentId);
    }

    @Override
    public String updateStudent(Integer studentId, Student student) {
        Student studentInfoInSQL =studentDao.getDetailById(studentId);
        if(studentInfoInSQL!=null){
            return studentDao.updateStudent(studentId,student);
        }else {
            return "update failed,cannot find the student!";
        }
    }
}
