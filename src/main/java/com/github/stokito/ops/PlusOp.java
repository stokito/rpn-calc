package com.github.stokito.ops;

public class PlusOp implements CalcBinaryOp {
    public static final PlusOp INSTANCE = new PlusOp();

    @Override
    public String getOp() {
        return "+";
    }

    @Override
    public double applyAsDouble(double a, double b) {
        return a + b;
    }
}
