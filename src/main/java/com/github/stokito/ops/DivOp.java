package com.github.stokito.ops;

public class DivOp implements CalcBinaryOp {
    public static final DivOp INSTANCE = new DivOp();

    @Override
    public String getOp() {
        return "/";
    }

    @Override
    public double applyAsDouble(double a, double b) {
        return a / b;
    }
}
