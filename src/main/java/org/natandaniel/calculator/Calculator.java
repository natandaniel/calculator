package org.natandaniel.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    private final List<String> TOKENS = new ArrayList<>();
    private int currentTokenIndex;
    private double memory;
    private boolean radians;

    public boolean isRadians() {
        return radians;
    }

    public void setRadians(boolean radians) {
        this.radians = radians;
    }

    public double getMemory() {
        return memory;
    }

    public void addToMemory(double value) {
        memory += value;
    }

    public void subtractFromMemory(double value) {
        memory -= value;
    }

    public void clearMemory() {
        memory = 0;
    }

    public double compute(String expression) {
        TOKENS.addAll(MathExpressionTokenizer.tokenize(expression));

        if (TOKENS.isEmpty()) return 0;

        try {
            double result = compute(radians);

            if (currentTokenIndex < TOKENS.size())
                throw new IllegalArgumentException("ERR: '" + TOKENS.get(currentTokenIndex) + "'");

            return result;
        }
        finally {
            TOKENS.clear();
            currentTokenIndex = 0;
        }
    }

    private double compute(boolean radians) {
        double result = parseTerm(radians);

        while (match("+", "-")) {
            String operator = previousToken();
            double rightOperand = parseTerm(radians);

            if (operator.equals("+")) result += rightOperand;
            else result -= rightOperand;
        }

        return result;
    }

    private double parseTerm(boolean radians) {
        double result = parseFactor(radians);

        while (match("x", "/", "%")) {
            String operator = previousToken();
            double rightOperand = parseFactor(radians);

            if (operator.equals("x")) result *= rightOperand;
            else if (operator.equals("/")) {
                if (rightOperand == 0) throw new ArithmeticException("ERR: division by 0");
                result /= rightOperand;
            }
            else result %= rightOperand;
        }

        return result;
    }

    private double parseFactor(boolean radians) {
        double result = parsePrimary(radians);

        while (match("^")) {
            double exponent = parsePrimary(radians);
            result = Math.pow(result, exponent);
        }

        return result;
    }

    private double parsePrimary(boolean radians) {
        if (match("(")) {
            double result = compute(radians);
            consume(")");
            return result;
        }

        if (match("sin", "cos", "tan", "exp", "ln", "√")) {
            String function = previousToken();
            consume("(");

            double argument = compute(radians);
            consume(")");

            if (function.equals("exp")) return Math.exp(argument);
            if (function.equals("ln")) return Math.log(argument);
            if (function.equals("√")) return Math.sqrt(argument);

            argument = radians ? argument : Math.toRadians(argument);
            if (function.equals("sin")) return Math.sin(argument);
            if (function.equals("cos")) return Math.cos(argument);
            if (function.equals("tan")) return Math.tan(argument);
        }

        if (matchNumber()) return Double.parseDouble(previousToken());
        else if (matchPi()) return Math.PI;

        throw new IllegalArgumentException("ERR: '" + TOKENS.get(currentTokenIndex) + "'");
    }

    private boolean match(String... expectedTokens) {
        if (currentTokenIndex < TOKENS.size()) {
            String currentToken = TOKENS.get(currentTokenIndex);

            for (String expectedToken : expectedTokens) {
                if (currentToken.equals(expectedToken)) {
                    currentTokenIndex++;
                    return true;
                }
            }
        }

        return false;
    }

    private boolean matchNumber() {
        if (currentTokenIndex < TOKENS.size()) {
            String currentToken = TOKENS.get(currentTokenIndex);

            if (currentToken.matches("\\d+(\\.\\d+)?")) {
                currentTokenIndex++;
                return true;
            }

            return false;
        }

        return false;
    }

    private boolean matchPi() {
        if (currentTokenIndex < TOKENS.size()) {
            String currentToken = TOKENS.get(currentTokenIndex);

            if (currentToken.matches("∏")) {
                currentTokenIndex++;
                return true;
            }
        }

        return false;
    }

    private String previousToken() {
        return TOKENS.get(currentTokenIndex - 1);
    }

    private void consume(String expectedToken) {
        if (match(expectedToken)) return;
        throw new IllegalArgumentException("ERR: expected '" + expectedToken + "'");
    }

    private static class MathExpressionTokenizer {

        private MathExpressionTokenizer() {}

        private static List<String> tokenize(String inputMathExpression) {
            List<String> tokens = new ArrayList<>();

            String operatorRegex = "[-+x/%]";
            String separatorRegex = "[().]";
            String functionRegex = "cos|sin|tan|exp|log|\\^|√|/|!";
            String numberRegex = "\\d+(\\.\\d+)?";
            String specialCharRegex = "∏";
            String tokenRegex =
                operatorRegex + "|" + separatorRegex + "|" + functionRegex + "|" + numberRegex +
                    "|" + specialCharRegex;

            Matcher tokenPatternMatcher = Pattern.compile(tokenRegex).matcher(inputMathExpression);

            while (tokenPatternMatcher.find())
                tokens.add(tokenPatternMatcher.group());

            return tokens;
        }
    }

}

