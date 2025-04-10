package com.example.calculadoracientifica.models;

import com.example.calculadoracientifica.utils.MathParser;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;

public class CalculadoraModel {
    private final List<String> calculationHistory = new ArrayList<>();
    private final MathParser parser = new MathParser();

    public String calculateResult(String expression) {
        try {
            String processedExpr = processExpression(expression);
            double result = evaluateExpression(processedExpr);
            String formattedResult = String.format("%.10g", result);

            calculationHistory.add(expression + " = " + formattedResult);
            return formattedResult;
        } catch (Exception e) {
            return "Erro";
        }
    }

    private String processExpression(String expr) {
        return expr.replace("^", "**")
                .replace("sin", "Math.sin")
                .replace("cos", "Math.cos")
                .replace("tan", "Math.tan")
                .replace("log10", "Math.log10")
                .replace("log", "Math.log")
                .replace("sqrt", "Math.sqrt")
                .replace("π", "Math.PI")
                .replace("e", "Math.E");
    }

    private double evaluateExpression(String expression) throws ScriptException {
        if (expression.contains("fact")) {
            return evaluateFactorial(expression);
        }

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
        Object result = engine.eval(expression);

        if (result instanceof Number) {
            return ((Number) result).doubleValue();
        } else {
            throw new ScriptException("Resultado inválido.");
        }
    }

    private double evaluateFactorial(String expression) {
        String numStr = expression.replaceAll(".*fact\\((\\d+)\\).*", "$1");
        int num = Integer.parseInt(numStr);

        long fact = 1;
        for (int i = 2; i <= num; i++) {
            fact *= i;
        }
        return fact;
    }

    public List<String> getCalculationHistory() {
        return new ArrayList<>(calculationHistory);
    }

    public String calculate(String input) {
        try {
            double result = parser.evaluate(input);
            String formattedResult = String.format("%.10g", result);
            calculationHistory.add(input + " = " + formattedResult);
            return formattedResult;
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }

    public void setRadiansMode(boolean radians) {
        parser.setRadiansMode(radians);
    }
}
