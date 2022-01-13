package com.example.demo;

public class AuthData {
    private String token;
    private String access;

    public AuthData(String token,String access){
        this.token = token;
        this.access = access;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }
}
