package com.example.calculadoracientifica.controller;

import com.example.calculadoracientifica.utils.MathParser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class CalculadoraController {

    @FXML private TextField display;

    private final MathParser parser = new MathParser();
    private final List<String> history = new ArrayList<>();
    private String currentInput = "";

    @FXML
    private void handleButtonClick(ActionEvent event) {
        Button button = (Button) event.getSource();
        String value = button.getText();

        switch (value) {
            case "C" -> currentInput = "";
            case "⌫" -> {
                if (!currentInput.isEmpty()) {
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                }
            }
            case "=" -> {
                try {
                    double result = parser.evaluate(currentInput);
                    history.add(currentInput + " = " + result);
                    currentInput = String.valueOf(result);
                } catch (Exception e) {
                    currentInput = "Erro: " + e.getMessage();
                }
            }
            case "x²" -> currentInput += "^2";
            case "1/x" -> currentInput = "1/(" + currentInput + ")";
            case "√" -> currentInput += "sqrt(";
            case "sin", "cos", "tan", "ln", "log", "exp", "asin", "acos", "atan" ->
                    currentInput += value + "(";
            case "π" -> currentInput += "pi";
            case "e" -> currentInput += "e";
            case "n!" -> currentInput = "fact(" + currentInput + ")";
            case "x^y", "xʸ" -> currentInput += "^";
            case "Hist" -> {
                showHistory();
                return;
            }
            default -> currentInput += value;
        }

        display.setText(currentInput);
    }


    @FXML
    private void onScientificButtonClick(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String cmd = btn.getText();

        switch (cmd) {
            case "sin⁻¹" -> currentInput += "asin(";
            case "cos⁻¹" -> currentInput += "acos(";
            case "tan⁻¹" -> currentInput += "atan(";
            case "xʸ" -> currentInput += "^";
            // outras funções...
        }

        display.setText(currentInput);
    }

    @FXML
    private void onEqualsClick() {
        try {
            double result = parser.evaluate(currentInput);
            String expressionResult = currentInput + " = " + result;
            history.add(expressionResult);
            currentInput = String.valueOf(result);
            display.setText(currentInput);
        } catch (Exception e) {
            display.setText("Erro: " + e.getMessage());
        }
    }

    private void showHistory() {
        if (history.isEmpty()) {
            display.setText("Histórico vazio.");
            return;
        }

        StringBuilder sb = new StringBuilder("Histórico:\n");
        for (String entry : history) {
            sb.append(entry).append("\n");
        }

        display.setText(sb.toString());
    }
}
