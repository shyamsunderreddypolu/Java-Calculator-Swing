package com.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorEngine {
    private String expression = "";

    public String getDisplayText() {
        return expression;
    }

    public void append(String input) {
        if (input == null || input.isBlank()) {
            return;
        }

        if (isOperator(input)) {
            if (expression.isEmpty()) {
                if ("-".equals(input)) {
                    expression = "-";
                }
                return;
            }

            if (lastCharIsOperator()) {
                expression = expression.substring(0, expression.length() - 1) + input;
                return;
            }

            expression += input;
            return;
        }

        if (".".equals(input)) {
            if (expression.isEmpty() || lastCharIsOperator()) {
                expression += "0.";
                return;
            }
            if (lastNumberContainsDot()) {
                return;
            }
        }

        expression += input;
    }

    public void evaluate() {
        if (expression.isBlank()) {
            return;
        }

        String sanitized = sanitizeExpression(expression);
        if (sanitized.isEmpty()) {
            throw new IllegalArgumentException("Invalid expression");
        }

        double result = compute(sanitized);
        expression = formatResult(result);
    }

    public void clear() {
        expression = "";
    }

    private boolean lastCharIsOperator() {
        if (expression.isEmpty()) {
            return false;
        }
        char last = expression.charAt(expression.length() - 1);
        return isOperator(String.valueOf(last));
    }

    private boolean isOperator(String value) {
        return "+-x/".contains(value);
    }

    private boolean lastNumberContainsDot() {
        int index = expression.length() - 1;
        while (index >= 0 && (Character.isDigit(expression.charAt(index)) || expression.charAt(index) == '.')) {
            if (expression.charAt(index) == '.') {
                return true;
            }
            index--;
        }
        return false;
    }

    private String sanitizeExpression(String value) {
        String sanitized = value.replace('x', '*').trim();
        while (!sanitized.isEmpty() && "+-*/".indexOf(sanitized.charAt(sanitized.length() - 1)) >= 0) {
            sanitized = sanitized.substring(0, sanitized.length() - 1).trim();
        }
        return sanitized;
    }

    private double compute(String expression) {
        Matcher matcher = Pattern.compile("\\d+(?:\\.\\d+)?|[+\\-*/]").matcher(expression);
        List<String> tokens = new ArrayList<>();
        while (matcher.find()) {
            tokens.add(matcher.group());
        }

        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Invalid expression");
        }

        if ("-".equals(tokens.get(0)) && tokens.size() > 1) {
            tokens.set(1, "-" + tokens.get(1));
            tokens.remove(0);
        }

        List<String> reduced = new ArrayList<>();
        reduced.add(tokens.get(0));
        for (int i = 1; i < tokens.size(); i += 2) {
            String op = tokens.get(i);
            String next = tokens.get(i + 1);
            if ("*".equals(op) || "/".equals(op)) {
                double left = Double.parseDouble(reduced.remove(reduced.size() - 1));
                double right = Double.parseDouble(next);
                double result = "*".equals(op) ? left * right : left / right;
                reduced.add(Double.toString(result));
            } else {
                reduced.add(op);
                reduced.add(next);
            }
        }

        double result = Double.parseDouble(reduced.get(0));
        for (int i = 1; i < reduced.size(); i += 2) {
            String op = reduced.get(i);
            double next = Double.parseDouble(reduced.get(i + 1));
            if ("+".equals(op)) {
                result += next;
            } else {
                result -= next;
            }
        }
        return result;
    }

    private String formatResult(double value) {
        String result = Double.toString(value);
        if (result.endsWith(".0")) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }
}
