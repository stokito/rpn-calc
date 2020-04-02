package com.github.stokito.ops;

public class NegOp implements CalcUnaryOp {
    public static final NegOp INSTANCE = new NegOp();

    @Override
    public String getOp() {
        return "neg";
    }

    @Override
    public double applyAsDouble(double a) {
        return -a;
    }
}
