package com.example.demo.dao;

import com.example.demo.UserInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInfoRowMapper implements RowMapper<UserInfo> {
    @Override
    public UserInfo mapRow(ResultSet resultSet, int i) throws SQLException {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(resultSet.getInt("id"));
        userInfo.setPassword(resultSet.getString("password"));
        userInfo.setName(resultSet.getString("name"));
        userInfo.setAccesses(resultSet.getString("accesses"));

        return userInfo;
    }
}
