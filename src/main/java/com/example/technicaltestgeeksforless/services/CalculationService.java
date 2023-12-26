package com.example.technicaltestgeeksforless.services;

import com.example.technicaltestgeeksforless.exceptions.RootException;
import net.sourceforge.jeval.Evaluator;
import org.springframework.stereotype.Service;

/**
 * Service for performing calculations to verify if a provided root value accurately satisfies a given equation.
 */
@Service
public class CalculationService {
    public void calculate(Double root, String equation) {
        Evaluator evaluator = new Evaluator();
        equation = equation.replaceAll("x", String.valueOf(root));
        String[] parts = equation.split("=");

        try {
            double leftPart = Double.parseDouble(evaluator.evaluate(parts[0]));
            double rightPart = Double.parseDouble(evaluator.evaluate(parts[1]));
            double difference = Math.abs(leftPart - rightPart);

            if (difference > 1e-9) {
                throw new RootException("Root \"" + root + "\" doesn't fit within the tolerance limit.");
            }
        } catch (Exception exception) {
            throw new RootException("Error calculating root: " + root);
        }
    }
}
