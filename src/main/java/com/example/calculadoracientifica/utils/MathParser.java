package com.example.calculadoracientifica.utils;

import java.util.*;
import java.util.regex.*;

public class MathParser {
    private boolean radiansMode = true;

    private static final Map<String, Integer> OPERATORS = Map.of(
            "+", 2, "-", 2, "*", 3, "/", 3, "^", 4, "%", 3
    );

    private static final Set<String> FUNCTIONS = Set.of(
            "sin", "cos", "tan", "log", "ln", "sqrt", "fact",
            "asin", "acos", "atan", "exp"
    );

    private static final Map<String, Double> CONSTANTS = Map.of(
            "π", Math.PI, "e", Math.E
    );

    public double evaluate(String expression) throws IllegalArgumentException {
        String processed = preProcess(expression);
        List<String> rpn = toRPN(processed);
        return evaluateRPN(rpn);
    }

    private String preProcess(String expr) {
        String processed = expr.replaceAll("\\s+", "").toLowerCase();

        for (Map.Entry<String, Double> entry : CONSTANTS.entrySet()) {
            processed = processed.replace(entry.getKey(), entry.getValue().toString());
        }

        // Multiplicação implícita: 2π -> 2*π, (2)(3) -> (2)*(3)
        processed = processed.replaceAll("(?<=[0-9)])(?=[(a-zπe])", "*");
        processed = processed.replaceAll("(?<![0-9])\\.(?=[0-9])", "0.");

        return processed;
    }

    private List<String> toRPN(String expr) {
        List<String> output = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();

        Pattern pattern = Pattern.compile("([0-9.]+|[a-z]+|\\^|\\+|-|\\*|/|%|\\(|\\)|,)");
        Matcher matcher = pattern.matcher(expr);

        while (matcher.find()) {
            String token = matcher.group();

            if (isNumber(token)) {
                output.add(token);
            } else if (FUNCTIONS.contains(token)) {
                stack.push(token);
            } else if (",".equals(token)) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
            } else if (isOperator(token)) {
                while (!stack.isEmpty() && isOperator(stack.peek()) &&
                        getPrecedence(token) <= getPrecedence(stack.peek())) {
                    output.add(stack.pop());
                }
                stack.push(token);
            } else if ("(".equals(token)) {
                stack.push(token);
            } else if (")".equals(token)) {
                while (!stack.isEmpty() && !"(".equals(stack.peek())) {
                    output.add(stack.pop());
                }
                if (!stack.isEmpty() && "(".equals(stack.peek())) {
                    stack.pop();
                }
                if (!stack.isEmpty() && FUNCTIONS.contains(stack.peek())) {
                    output.add(stack.pop());
                }
            }
        }

        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }

        return output;
    }

    private double evaluateRPN(List<String> rpn) {
        Deque<Double> stack = new ArrayDeque<>();

        for (String token : rpn) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (FUNCTIONS.contains(token)) {
                if (stack.isEmpty()) throw new IllegalArgumentException("Argumento insuficiente para função " + token);
                double arg = stack.pop();
                stack.push(applyFunction(token, arg));
            } else if (isOperator(token)) {
                if (stack.size() < 2) throw new IllegalArgumentException("Argumento insuficiente para operador " + token);
                double b = stack.pop();
                double a = stack.pop();
                stack.push(applyOperator(token, a, b));
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Expressão inválida");
        }

        return stack.pop();
    }

    private boolean isNumber(String token) {
        return token.matches("-?\\d+(\\.\\d+)?");
    }

    private boolean isOperator(String token) {
        return OPERATORS.containsKey(token);
    }

    private int getPrecedence(String operator) {
        return OPERATORS.getOrDefault(operator, 0);
    }

    private double applyFunction(String function, double arg) {
        switch (function) {
            case "sin": return radiansMode ? Math.sin(arg) : Math.sin(Math.toRadians(arg));
            case "cos": return radiansMode ? Math.cos(arg) : Math.cos(Math.toRadians(arg));
            case "tan": return radiansMode ? Math.tan(arg) : Math.tan(Math.toRadians(arg));
            case "asin": return radiansMode ? Math.asin(arg) : Math.toDegrees(Math.asin(arg));
            case "acos": return radiansMode ? Math.acos(arg) : Math.toDegrees(Math.acos(arg));
            case "atan": return radiansMode ? Math.atan(arg) : Math.toDegrees(Math.atan(arg));
            case "log": return Math.log10(arg);
            case "ln": return Math.log(arg);
            case "sqrt": return Math.sqrt(arg);
            case "fact": return factorial((int) arg);
            case "exp": return Math.exp(arg);
            default: throw new UnsupportedOperationException("Função não suportada: " + function);
        }
    }

    private double applyOperator(String operator, double a, double b) {
        switch (operator) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/":
                if (b == 0) throw new ArithmeticException("Divisão por zero");
                return a / b;
            case "^": return Math.pow(a, b);
            case "%": return a % b;
            default: throw new UnsupportedOperationException("Operador não suportado: " + operator);
        }
    }

    private double factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("Fatorial de número negativo");
        if (n > 20) throw new IllegalArgumentException("Número muito grande para fatorial");
        long result = 1;
        for (int i = 2; i <= n; i++) result *= i;
        return result;
    }

    public void setRadiansMode(boolean radiansMode) {
        this.radiansMode = radiansMode;
    }
}
