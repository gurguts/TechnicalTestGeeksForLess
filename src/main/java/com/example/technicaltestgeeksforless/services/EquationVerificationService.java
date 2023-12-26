package com.example.technicaltestgeeksforless.services;

import com.example.technicaltestgeeksforless.exceptions.EquationException;
import org.springframework.stereotype.Service;

/**
 * Service for verifying the structural integrity and correctness of mathematical equations.
 */
@Service
public class EquationVerificationService {
    public void verify(String equation) {
        if (equation == null) {
            throw new EquationException("Equation is null!");
        }
        if (equation.isEmpty()) {
            throw new EquationException("Equation is empty!");
        }
        checkEquation(equation);
    }

    public static void checkEquation(String equation) {
        String[] parts = equation.split("=");
        if (parts.length != 2) {
            throw new EquationException("The equation has more or less than one \"=\" sign.");
        }
        checkParentheses(parts[0]);
        checkParentheses(parts[1]);
        validateConsecutiveOperators(equation);
    }

    private static void checkParentheses(String part) {
        int balance = 0;
        for (int i = 0; i < part.length(); i++) {
            char c = part.charAt(i);
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;
            }
            if (balance < 0) {
                throw new EquationException("The equation has more closing parentheses than opening.");
            }
        }
        if (balance != 0) {
            throw new EquationException("the equation has a different number of opening or closing parentheses.");
        }
    }

    public static void validateConsecutiveOperators(String equation) {
        String validOperators = "+-*/";
        boolean lastWasOperator = false;

        for (int i = 0; i < equation.length(); i++) {
            char c = equation.charAt(i);

            if (validOperators.indexOf(c) >= 0) {
                if (lastWasOperator && c != '-') {
                    throw new EquationException("Consecutive operators found");
                }
                lastWasOperator = true;
            } else {
                lastWasOperator = false;
            }
        }
    }
}
