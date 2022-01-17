package com.example.demo;

public class UserInfo {
    Integer id;
    String password;
    String name;
    String accesses;

    public String getAccesses() {
        return accesses;
    }

    public void setAccesses(String accesses) {
        this.accesses = accesses;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer  id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
