package com.github.stokito;

import java.text.NumberFormat;

import static java.lang.System.in;
import static java.lang.System.out;

public class Main {

    private static final NumberFormat FORMATTER = NumberFormat.getInstance();

    public static void main(String[] args) {
        Calc calc = new Calc();
        calc.setFormatter(FORMATTER);
        new StreamProcessor(calc, in, out, FORMATTER).process();
    }

}
