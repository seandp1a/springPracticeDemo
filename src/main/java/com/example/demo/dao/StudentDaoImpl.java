package com.example.demo.dao;

import com.example.demo.Student;
import com.example.demo.StudentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StudentDaoImpl implements StudentDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Student getById(Integer studentId) {
        String sql="select id,name FROM student where id = :studentId";

        Map<String, Object> map = new HashMap<>();
        map.put("studentId",studentId);

        List<Student> list = namedParameterJdbcTemplate.query(sql,map,new StudentRowMapper());
        // 第三個參數 => 自定義一個 class 去 implements RowMapper<你要回傳的型別>，他會把sql查到的資料解析弄成我要的樣子丟回來

        if(list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }
}
