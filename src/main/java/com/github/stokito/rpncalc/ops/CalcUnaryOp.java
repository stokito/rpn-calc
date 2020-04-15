package com.github.stokito.rpncalc.ops;

import java.util.function.DoubleUnaryOperator;

public interface CalcUnaryOp extends DoubleUnaryOperator, CalcOp {
    default int getOperandsCount() {
        return 1;
    }
}
