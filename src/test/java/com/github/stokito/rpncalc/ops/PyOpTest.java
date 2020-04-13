package com.github.stokito.rpncalc.ops;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PyOpTest {

    @Test
    void apply() {
        PyOp pyOp = new PyOp();
        assertEquals(1, pyOp.apply(5 ,12, 13));
        assertEquals(0, pyOp.apply(5 ,12, 14));
    }
}