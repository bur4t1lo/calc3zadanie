package com.example.core;

import java.util.HashMap;
import java.util.Map;

public class Calculator {
    public enum OpType {
        Add, Sub, Mul, Div, Let, None
    }

    static class TypeAndExpressions {
        OpType type;
        String[] expressions;
    }

    public int calculate(String expression, Map<String, Integer> map) {
        TypeAndExpressions typeAndExpressions = fetchTypeAndExpressionsFromString(expression);
        switch (typeAndExpressions.type) {
            case Add:
                int a1 = calculate(typeAndExpressions.expressions[0], map);
                int b1 = calculate(typeAndExpressions.expressions[1], map);
                return a1 + b1;
            case Sub:
                int a2 = calculate(typeAndExpressions.expressions[0], map);
                int b2 = calculate(typeAndExpressions.expressions[1], map);
                return a2 - b2;
            case Mul:
                int a3 = calculate(typeAndExpressions.expressions[0], map);
                int b3 = calculate(typeAndExpressions.expressions[1], map);
                return a3 * b3;
            case Div:
                int a4 = calculate(typeAndExpressions.expressions[0], map);
                int b4 = calculate(typeAndExpressions.expressions[1], map);
                return a4 / b4;
            case Let:
                int a5 = calculate(typeAndExpressions.expressions[1], map);
                Map<String, Integer> newMap = new HashMap<>();
                if (map != null) {
                    newMap.putAll(map);
                }
                newMap.put(typeAndExpressions.expressions[0], a5);
                return calculate(typeAndExpressions.expressions[2], newMap);
            case None:
                if (map != null && map.containsKey(typeAndExpressions.expressions[0])) {
                    return map.get(typeAndExpressions.expressions[0]);
                }
                return Integer.parseInt(typeAndExpressions.expressions[0]);
        }
        return 0;
    }

    private TypeAndExpressions fetchTypeAndExpressionsFromString(String exp) {
        TypeAndExpressions typeAndExpressions = new TypeAndExpressions();
        if (exp.length() < 4) {
            typeAndExpressions.type = OpType.None;
            typeAndExpressions.expressions = new String[]{exp};
        } else {
            if (exp.startsWith("add(")) {
                typeAndExpressions.type = OpType.Add;
                typeAndExpressions.expressions = getTwoSubexpression(exp.substring(3));
            } else if (exp.startsWith("sub(")) {
                typeAndExpressions.type = OpType.Sub;
                typeAndExpressions.expressions = getTwoSubexpression(exp.substring(3));
            } else if (exp.startsWith("mult(")) {
                typeAndExpressions.type = OpType.Mul;
                typeAndExpressions.expressions = getTwoSubexpression(exp.substring(4));
            } else if (exp.startsWith("div(")) {
                typeAndExpressions.type = OpType.Div;
                typeAndExpressions.expressions = getTwoSubexpression(exp.substring(3));
            } else if (exp.startsWith("let(")) {
                typeAndExpressions.type = OpType.Let;
                typeAndExpressions.expressions = getThreeSubexpression(exp.substring(3));
            } else {
                typeAndExpressions.type = OpType.None;
                typeAndExpressions.expressions = new String[]{exp};
            }
        }
        return typeAndExpressions;
    }

    private String[] getTwoSubexpression(String exp) {
        int noOfOpenparan = 0;
        String[] expressions = new String[2];
        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == '(') {
                noOfOpenparan++;
            } else if (exp.charAt(i) == ')') {
                noOfOpenparan--;
            } else if (exp.charAt(i) == ',' && noOfOpenparan == 1) {
                expressions[0] = exp.substring(1, i).trim();
                expressions[1] = exp.substring(i + 1, exp.length() - 1).trim();
                return expressions;
            }
        }
        return null;
    }

    private String[] getThreeSubexpression(String exp) {
        int noOfOpenparan = 0;
        int firstCommaIndex = 0, secondCommaIndex = 0;
        String[] expressions = new String[3];
        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == '(') {
                noOfOpenparan++;
            } else if (exp.charAt(i) == ')') {
                noOfOpenparan--;
            } else if (exp.charAt(i) == ',' && noOfOpenparan == 1) {
                if (firstCommaIndex == 0) {
                    firstCommaIndex = i;
                } else {
                    secondCommaIndex = i;
                    break;
                }
            }
        }
        expressions[0] = exp.substring(1, firstCommaIndex).trim();
        expressions[1] = exp.substring(firstCommaIndex + 1, secondCommaIndex).trim();
        expressions[2] = exp.substring(secondCommaIndex + 1, exp.length() - 1).trim();
        return expressions;
    }
}
