package com.kranti.rpncalculator.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Stack;
import java.util.function.BiFunction;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kranti.rpncalculator.exception.CalculationException;

@Component
public class CalculationUtility {

    private static String supportedOperators;

    public static String getSupportedOperators() {
        return supportedOperators;
    }

    @Value("${rpncalculator.supportedOperators}")
    public void setSupportedOperators(String supportedOperators) {
        CalculationUtility.supportedOperators = supportedOperators;
    }

    public static String sanitizeExpression(String expression) {
        if (expression != null) {
            expression = expression.trim().replaceAll("\\s+", " ");
        }
        return expression;
    }

    public static boolean isValidExpression(String expression) {
        boolean isValid = true;
        String message = "";
        int exprLength = expression.split(" ").length;
        if (expression == null || expression.isEmpty()) {
            message = "RPN Expression is Empty or null.";
        } else if (exprLength < 3 || exprLength % 2 == 0) {
            message = "Invalid expression.";
        } else if (!hasEnoughSpaces(expression)) {
            message = "Expression should have ' ' as separator between each entity of expression.";
        } else if (!hasOperators(expression)) {
            message = "Sufficient operators may not provided or unsupported operators provided. Supported Operators are :"
                    + getSupportedOperators();
        }
        if (!message.isEmpty()) {
            throw new CalculationException(message);
        }
        return isValid;
    }

    public static BigDecimal calculate(String input) {
        Stack<BigDecimal> numbers = new Stack<>();
        Arrays.asList(input.split(" ")).stream().forEach(number -> {
            switch (number) {
            case "+":
                buildStack(numbers, (n1, n2) -> n2.add(n1));
                break;
            case "-":
                buildStack(numbers, (n1, n2) -> n2.subtract(n1));
                break;
            case "*":
                buildStack(numbers, (n1, n2) -> n2.multiply(n1));
                break;
            case "/":
                buildStack(numbers, (n1, n2) -> n2.divide(n1));
                break;
            default:
                numbers.push(new BigDecimal(number));
            }
        });
        return numbers.pop();
    }

    private static boolean hasEnoughSpaces(String expression) {
        boolean hasSpaces = false;
        int spaceCount = StringUtils.countMatches(expression, " ");
        int exprLength = expression.split(" ").length;
        if (exprLength - spaceCount == 1) {
            hasSpaces = true;
        }
        return hasSpaces;
    }

    private static boolean hasOperators(String expression) {
        boolean hasOperators = false;
        String[] exprComps = expression.split(" ");
        int numOperators = 0;
        for (String comp : exprComps) {
            System.err.println();
            if (!NumberUtils.isCreatable(comp) && getSupportedOperators().contains(comp)) {
                numOperators += 1;
            }
        }
        int numNumerics = exprComps.length - numOperators;
        if (numNumerics - numOperators == 1) {
            hasOperators = true;
        }
        return hasOperators;
    }

    private static void buildStack(Stack<BigDecimal> numbers,
            BiFunction<BigDecimal, BigDecimal, BigDecimal> operation) {
        numbers.push(operation.apply(numbers.pop(), numbers.pop()));
    }
}
