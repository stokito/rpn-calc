package com.github.stokito.rpncalc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalcTest {

    @Test
    void sqrEquation() throws Exception {
        Calc calc = new Calc();
        Double result = calc.eval("5 12 13 py");
        assertEquals(1, result);
    }
}