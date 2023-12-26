package com.example.technicaltestgeeksforless.services;

import com.example.technicaltestgeeksforless.exceptions.RootException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculationServiceTest {
    private CalculationService service;

    @BeforeEach
    void setUp() {
        service = new CalculationService();
    }

    @Test
    void calculate_CorrectRoot_NoExceptionThrown() {
        double root = 1.0;
        String equation = "x+2=3";

        assertDoesNotThrow(() -> service.calculate(root, equation));
    }

    @Test
    void calculate_IncorrectRoot_ThrowsException() {
        double root = 2.0;
        String equation = "x+2=3";

        assertThrows(RootException.class, () -> service.calculate(root, equation));
    }

    @Test
    void calculate_ErrorInCalculation_ThrowsException() {
        double root = 1.0;
        String equation = "invalidEquation";

        assertThrows(RootException.class, () -> service.calculate(root, equation));
    }
}
