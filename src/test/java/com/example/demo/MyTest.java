package com.example.demo;

import org.junit.jupiter.api.*;

public class MyTest {
    @BeforeAll
    public static void beforeAll(){
        System.out.println("run @BeforeAll");
    }
    @AfterAll
    public static void afterAll(){
        System.out.println("run @AfterAll");
    }
    @BeforeEach
    public void beforeEach(){
        System.out.println("run @BeforeEach");
    }
    @AfterEach
    public void afterEach(){
        System.out.println("run @AfterEach");
    }
    @Test
    public void test1(){
        System.out.println("run test1");
    }
    @Test
    public void test2(){
        System.out.println("run test2");
    }

}
