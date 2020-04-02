package com.github.stokito.rpncalc;

import com.github.stokito.rpncalc.ops.Ops;

import java.text.NumberFormat;
import java.util.*;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import static java.util.Arrays.asList;
import static java.util.Collections.addAll;

public class Calc {
    /** Operators: [Name, Func]. Can be injected with DI */
    private Map<String, Object> ops = Ops.OPS;
    private NumberFormat formatter = NumberFormat.getInstance();
    private final Deque<Double> values = new ArrayDeque<>();

    public Double eval(String op) throws Exception {
        List<String> input = splitExpression(op);

        for (String token : input) {
            Object func = ops.get(token);
            if (func != null) {
                if (func instanceof DoubleUnaryOperator) {
                    if (values.size() < 1) {
                        throw new Exception("Not enough operands");
                    }
                    double num = values.pop();
                    DoubleUnaryOperator operator = (DoubleUnaryOperator) func;
                    double result = operator.applyAsDouble(num);
                    values.push(result);
                } else {
                    assert func instanceof DoubleBinaryOperator;
                    if (values.size() < 2) {
                        throw new Exception("Not enough operands");
                    }
                    double right = values.pop();
                    double left = values.pop();
                    DoubleBinaryOperator operator = (DoubleBinaryOperator) func;
                    double result = operator.applyAsDouble(left, right);
                    values.push(result);
                }
            } else {
                // push to stack if it is a number
                double number = formatter.parse(token).doubleValue();
                values.push(number);
            }
        }

        Double result = values.peek();
        return result;
    }

    private List<String> splitExpression(String op) {
        assert op != null && !op.isBlank();
        String[] tokens = op.trim().split(" ");
        List<String> input = new ArrayList<>(tokens.length);
        addAll(input, tokens);
        input.removeAll(asList(null, ""));
        return input;
    }

    public void clear() {
        values.clear();
    }

    public Deque<Double> getValues() {
        return values;
    }

    public void setOps(Map<String, Object> ops) {
        this.ops = ops;
    }

    public Map<String, Object> getOps() {
        return ops;
    }

    public void setFormatter(NumberFormat formatter) {
        this.formatter = formatter;
    }

    public NumberFormat getFormatter() {
        return formatter;
    }
}
