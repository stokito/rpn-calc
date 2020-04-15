package com.github.stokito.rpncalc.ops;

import java.util.function.DoubleBinaryOperator;

public interface CalcBinaryOp extends DoubleBinaryOperator, CalcOp {
    default int getOperandsCount() {
        return 2;
    }
}
