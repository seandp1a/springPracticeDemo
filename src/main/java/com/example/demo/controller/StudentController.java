package com.example.demo.controller;

import com.example.demo.Student;
import com.example.demo.StudentRowMapper;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private StudentService studentService;

    @PostMapping("/students")
    public String insert(@RequestBody Student student) {
        String sql = "INSERT INTO student(NAME) VALUE (:studentName)";
        // sql語法中，冒號後面的值帶表示一個動態變數
        Map<String ,Object> map = new HashMap<>();
        map.put("studentName",student.getName());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        // 透過keyHolder可以取的auto_increment 的 primary key，這次執行的值是多少
        int id = keyHolder.getKey().intValue();
        // 若資料庫key的資料型別是long，此處就必須使用.getKey.longValue()

        System.out.println("mySql auto increment id : "+ id);
        return "Insert Success! Name："+ student.getName() +", id："+id;
    }

    @DeleteMapping("/students/{studentId}")
    public String delete(@PathVariable Integer studentId){
        String sql = "DELETE FROM student WHERE id = :studentId";

        Map<String ,Object> map = new HashMap<>();
        map.put("studentId",studentId);

        namedParameterJdbcTemplate.update(sql,map);
        return "do Delete!";
    }

    @PostMapping("/students/batch")
    public String insertList(@RequestBody List<Student> studentList){
        String sql = "insert into student(name) value (:studentName)";
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[studentList.size()];

        for(int i=0;i<studentList.size();i++){
            Student student = studentList.get(i);

            parameterSources[i]= new MapSqlParameterSource();
            parameterSources[i].addValue("studentName",student.getName());
        }

        namedParameterJdbcTemplate.batchUpdate(sql,parameterSources);
        return ("insert batch data success!");
    }

    @GetMapping("/students/{studentId}")
    public Student selectOneStudent(@PathVariable Integer studentId){
        return studentService.getById(studentId);
    }

    @GetMapping("/students")
    public List<Student> select(){
        String sql="select id,name FROM student";
        Map<String, Object> map = new HashMap<>();

        List<Student> list = namedParameterJdbcTemplate.query(sql,map,new BeanPropertyRowMapper<>(Student.class));
        // 第三個參數 => 自定義一個 class 去 implements RowMapper<你要回傳的型別>，他會把sql查到的資料解析弄成我要的樣子丟回來
        return list;
    }



}
