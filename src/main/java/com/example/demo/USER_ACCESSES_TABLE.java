package com.example.demo;

public enum USER_ACCESSES_TABLE {
    ROLE_ROOT(1),
    ROLE_STUDENT(2);

    private final int code;

    private USER_ACCESSES_TABLE(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        //only override toString, if the returned value has a meaning for the
        //human viewing this value
        return String.valueOf(code);
    }
}
