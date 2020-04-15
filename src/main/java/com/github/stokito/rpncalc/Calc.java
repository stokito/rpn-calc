package com.github.stokito.rpncalc;

import com.github.stokito.rpncalc.ops.*;

import java.text.NumberFormat;
import java.util.*;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import static java.util.Arrays.asList;
import static java.util.Collections.addAll;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

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
                if (func instanceof CalcUnaryOp) {
                    CalcUnaryOp operator = (CalcUnaryOp) func;
                    isEnoughOperands(operator.getOperandsCount());
                    double num = values.pop();
                    double result = operator.applyAsDouble(num);
                    values.push(result);
                } else if (func instanceof CalcBinaryOp) {
                    CalcBinaryOp operator = (CalcBinaryOp) func;
                    isEnoughOperands(operator.getOperandsCount());
                    double right = values.pop();
                    double left = values.pop();
                    double result = operator.applyAsDouble(left, right);
                    values.push(result);
                } else if (func instanceof PyOp) {
                    PyOp operator = (PyOp) func;
                    isEnoughOperands(operator.getOperandsCount());
                    double val3 = values.pop();
                    double val2 = values.pop();
                    double val1 = values.pop();
                    double result = operator.apply(val1, val2, val3);
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

    private void isEnoughOperands(int operandsCount) throws Exception {
        if (values.size() < operandsCount) {
            throw new Exception("Not enough operands");
        }
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

    public void setBasicOps(List<CalcOp> ops) {
        Map<String, Object> basicOpsMap = ops.stream().collect(toMap(CalcOp::getOp, identity()));
        setOps(basicOpsMap);
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
