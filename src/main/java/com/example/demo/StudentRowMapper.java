package com.example.demo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
        // resultSet用來存放從Sql查詢出來的數據
        // Student 決定將這些數據轉換成Student類型的Java Object
        Student student = new Student();
        student.setId(resultSet.getInt("id"));
        // 因為資料庫id欄位是int，故此處用getInt()將名稱為"id"的欄位的值取出來
        student.setName(resultSet.getString("name"));
        // 因為資料庫name欄位是VARCHAR，故此處用getString()將名稱為"name"的欄位的值取出來

        return student;
    }
}
