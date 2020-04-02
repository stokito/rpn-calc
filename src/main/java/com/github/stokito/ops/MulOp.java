package com.github.stokito.ops;

public class MulOp implements CalcBinaryOp {
    public static final MulOp INSTANCE = new MulOp();

    @Override
    public String getOp() {
        return "*";
    }

    @Override
    public double applyAsDouble(double a, double b) {
        return a * b;
    }
}
