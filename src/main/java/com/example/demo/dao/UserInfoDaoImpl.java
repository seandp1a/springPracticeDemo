package com.example.demo.dao;

import com.example.demo.Student;
import com.example.demo.StudentRowMapper;
import com.example.demo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserInfoDaoImpl implements UserInfoDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public UserInfo getUserById(String userId) {
        String sql = "SELECT id,password,name from user where id = :userId";
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);

        List<UserInfo> list = namedParameterJdbcTemplate.query(sql,map,new UserInfoRowMapper());
        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    public UserInfo getUserByName(String userName){
        String sql = "SELECT id,password,name from user where name = :userName";
        Map<String,Object> map = new HashMap<>();
        map.put("userName",userName);

        List<UserInfo> list = namedParameterJdbcTemplate.query(sql,map,new UserInfoRowMapper());
        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }
}
