package com.example.demo.dao;

import com.example.demo.Student;
import com.example.demo.StudentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StudentDaoImpl implements StudentDao{

    private static Student mapRow(ResultSet resultSet, int i) throws SQLException {
        // resultSet用來存放從Sql查詢出來的數據
        // Student 決定將這些數據轉換成Student類型的Java Object
        Student student = new Student();
        student.setId(resultSet.getInt("id"));
        // 因為資料庫id欄位是int，故此處用getInt()將名稱為"id"的欄位的值取出來
        student.setName(resultSet.getString("name"));
        // 因為資料庫name欄位是VARCHAR，故此處用getString()將名稱為"name"的欄位的值取出來

        return student;
    }
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Student getDetailById(Integer studentId) {
        String sql="select student.id , student.name , student.age ,detail.score, detail.graduate, student.create_date FROM student inner Join detail " +
                "on student.id=detail.student_id where student.id = :studentId";
        Map<String, Object> map = new HashMap<>();

        map.put("studentId",studentId);
        System.out.println(map);
        List<Student> list = namedParameterJdbcTemplate
                .query(sql,map,new BeanPropertyRowMapper<>(Student.class));

        if(list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }

    @Override
    public List<Student> getStudentList() {
        String sql="select name FROM student";
        Map<String, Object> map = new ConcurrentHashMap<>();
        List<Student> list = namedParameterJdbcTemplate
                .query(sql,map,new BeanPropertyRowMapper<>(Student.class));
        // 第三個參數 => 自定義一個 class 去 implements RowMapper<你要回傳的型別>，他會把sql查到的資料解析弄成我要的樣子丟回來
        return list;
    }

    @Override
    public String insertStudent(Student student) {
        String sql = "INSERT INTO student(NAME,age) VALUE (:studentName,:studentAge)";
        Map<String ,Object> map = new HashMap<>();
        map.put("studentName",student.getName());
        map.put("studentAge",student.getAge());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        System.out.println(new MapSqlParameterSource(map));
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

        int id = keyHolder.getKey().intValue();
        return "新增成功! id : "+id+",name："+student.getName();
    }

    @Override
    public String insertStudentList(List<Student> studentList) {
        String sql = "insert into student(name,age) value (:studentName,:studentAge)";
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[studentList.size()];

        for(int i=0;i<studentList.size();i++){
            Student student = studentList.get(i);

            parameterSources[i]= new MapSqlParameterSource();
            parameterSources[i].addValue("studentName",student.getName());
            parameterSources[i].addValue("studentAge",student.getAge());
        }
        System.out.println(parameterSources.toString());
        namedParameterJdbcTemplate.batchUpdate(sql,parameterSources);
        return ("insert batch data success!");
    }

    @Override
    public String deleteStudent(Integer studentId) {
        String sql = "DELETE FROM student WHERE id = :studentId";

        Map<String ,Object> map = new HashMap<>();
        map.put("studentId",studentId);

        namedParameterJdbcTemplate.update(sql,map);
        return "do Delete!";
    }

    @Override
    public String updateStudent(Integer studentId,Student student) {
        String studentName = student.getName();
        Integer studentAge = student.getAge();
        String sql = "update student set name=:studentName,age=:studentAge where id = :studentId";

        Map<String ,Object> map = new HashMap<>();
        map.put("studentId",studentId);
        map.put("studentName",studentName);
        map.put("studentAge",studentAge);

        namedParameterJdbcTemplate.update(sql,map);
        return "student id = "+studentId+" update success!";
    }
}
