package com.github.stokito.rpncalc.ops;

/** Calculator operator or function e.g. + - pow */
public interface CalcOp {
    String getOp();
    int getOperandsCount();
}
