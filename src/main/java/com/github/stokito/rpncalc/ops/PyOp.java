package com.github.stokito.rpncalc.ops;

public class PyOp implements CalcOp {
    public static final CalcOp INSTANCE = new PyOp();

    @Override
    public String getOp() {
        return "py";
    }

    public double apply(double a, double b, double c) {
        boolean result = a * a + b * b == c * c;
        return result ? 1 : 0;
    }
}
