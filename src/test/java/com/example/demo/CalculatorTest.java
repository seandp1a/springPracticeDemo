package com.example.demo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    @Disabled
    @Test
    public void add(){
        Calculator calculator = new Calculator();
        int result = calculator.add(1,2);

        assertNotNull(result);
        assertTrue(result > 1);
        assertEquals(3,result,"可補充錯誤說明");
        // assert = 斷言,故此句斷言result=3的意思
    }
    @DisplayName("test divide")
    @Test
    public void divide(){
        Calculator calculator = new Calculator();
        assertThrows(ArithmeticException.class,() -> {
            calculator.divide(1,0);
        });
        // 判斷除以零會不會噴錯 (ArithmeticException->整數除0會噴的錯誤)
    }
}