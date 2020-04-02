package com.github.stokito.rpncalc.ops;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/** basic operators and functions supported by the calc */
public class Ops {
    public static final Map<String, Object> OPS = new HashMap<>(48, 1.0F);
    private static final List<CalcOp> BASIC_OPS = List.of(
            PlusOp.INSTANCE,
            MinusOp.INSTANCE,
            MulOp.INSTANCE,
            DivOp.INSTANCE,
            NegOp.INSTANCE
    );

    static {
        addBasicOps(OPS);
        addOpsFromClass(Math.class, OPS);
    }

    private static void addBasicOps(Map<String, Object> ops) {
        Map<String, CalcOp> basicOpsMap = BASIC_OPS.stream().collect(toMap(CalcOp::getOp, identity()));
        ops.putAll(basicOpsMap);
    }


    public static void addOpsFromClass(Class klass, Map<String, Object> ops) {
        MethodType typeUnary = MethodType.methodType(double.class, double.class);
        MethodType invokedTypeUnary = MethodType.methodType(DoubleUnaryOperator.class);
        MethodType typeBinary = MethodType.methodType(double.class, double.class, double.class);
        MethodType invokedTypeBinary = MethodType.methodType(DoubleBinaryOperator.class);
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        for (Method m : klass.getDeclaredMethods()) {
            if (!Modifier.isStatic(m.getModifiers()) || !Modifier.isPublic(m.getModifiers())) {
                continue;
            }
            try {
                MethodHandle mh = lookup.unreflect(m);
                if (mh.type().equals(typeUnary)) {
                    DoubleUnaryOperator func = (DoubleUnaryOperator) LambdaMetafactory.metafactory(
                            lookup, "applyAsDouble", invokedTypeUnary, typeUnary, mh, typeUnary).getTarget().invokeExact();
                    ops.put(m.getName(), func);
                } else if (mh.type().equals(typeBinary)) {
                    DoubleBinaryOperator func = (DoubleBinaryOperator) LambdaMetafactory.metafactory(
                            lookup, "applyAsDouble", invokedTypeBinary, typeBinary, mh, typeBinary).getTarget().invokeExact();
                    ops.put(m.getName(), func);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
