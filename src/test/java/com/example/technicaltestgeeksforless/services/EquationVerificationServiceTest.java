package com.example.technicaltestgeeksforless.services;

import com.example.technicaltestgeeksforless.exceptions.EquationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EquationVerificationServiceTest {
    private EquationVerificationService service;

    @BeforeEach
    void setUp() {
        service = new EquationVerificationService();
    }

    @Test
    void verify_NullEquation_ThrowsException() {
        assertThrows(EquationException.class, () -> service.verify(null));
    }

    @Test
    void verify_EmptyEquation_ThrowsException() {
        assertThrows(EquationException.class, () -> service.verify(""));
    }

    @Test
    void verify_ValidEquation_NoExceptionThrown() {
        assertDoesNotThrow(() -> service.verify("x*2+3*x+20=0"));
    }

    @Test
    void checkEquation_SingleEqualSign_NoExceptionThrown() {
        assertDoesNotThrow(() -> EquationVerificationService.checkEquation("x*2*x*x+3*x*x*x+2=0"));
    }

    @Test
    void checkEquation_MultipleEqualSigns_ThrowsException() {
        assertThrows(EquationException.class, () -> EquationVerificationService.checkEquation("x*2660*x+3*x+=2=0"));
    }

    @Test
    void checkEquation_UnequalParentheses_ThrowsException() {
        assertThrows(EquationException.class, () -> EquationVerificationService.checkEquation("x*(2+3*x)=869)"));
    }

    @Test
    void checkEquation_ConsecutiveOperators_ThrowsException() {
        assertThrows(EquationException.class, () -> EquationVerificationService.checkEquation("x*578++3*x=2"));
    }

    @Test
    void checkEquation_CorrectParentheses_NoExceptionThrown() {
        assertDoesNotThrow(() -> EquationVerificationService.checkEquation("x*x*x*(2+3)=2"));
    }
    @Test
    void checkEquation_MoreClosingThanOpeningParentheses_ThrowsException() {
        assertThrows(EquationException.class, () -> EquationVerificationService.checkEquation("7*x*2+3)=2("));
    }
    @Test
    void checkEquation_DifferentNumberOfOpeningOrClosingParentheses_ThrowsException() {
        assertThrows(EquationException.class, () -> EquationVerificationService.checkEquation("(x*58+3*x=2"));
    }

    @Test
    void validateConsecutiveOperators_ConsecutiveOperators_ThrowsException() {
        assertThrows(EquationException.class, () -> EquationVerificationService.validateConsecutiveOperators("x*2++378*x*x=2"));
    }
    @Test
    void validateConsecutiveOperators_ConsecutiveOperatorsIncludingMinus_NoExceptionThrown() {
        assertDoesNotThrow(() -> EquationVerificationService.validateConsecutiveOperators("x*2+-3*x=24"));
    }
    @Test
    void validateConsecutiveOperators_NoConsecutiveOperators_NoExceptionThrown() {
        assertDoesNotThrow(() -> EquationVerificationService.validateConsecutiveOperators("x*x*2+3*x=2"));
    }

}
