package com.github.stokito.ops;

public class MinusOp implements CalcBinaryOp {
    public static final MinusOp INSTANCE = new MinusOp();

    @Override
    public String getOp() {
        return "-";
    }

    @Override
    public double applyAsDouble(double a, double b) {
        return a - b;
    }
}
