package com.example.technicaltestgeeksforless.services;

import com.example.technicaltestgeeksforless.dto.EquationDTO;
import com.example.technicaltestgeeksforless.exceptions.RootException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RootVerificationServiceTest {
    private RootVerificationService service;

    @BeforeEach
    void setUp() {
        service = new RootVerificationService();
    }

    @Test
    void verify_NoRoot_ThrowsException() {
        EquationDTO dto = new EquationDTO();
        dto.setRoot(null);

        assertThrows(RootException.class, () -> service.verify(dto));
    }

    @Test
    void verify_InvalidRootFormat_ThrowsException() {
        EquationDTO dto = new EquationDTO();
        dto.setRoot("invalid");

        assertThrows(RootException.class, () -> service.verify(dto));
    }

    @Test
    void verify_ValidRoot_ReturnsRoot() {
        EquationDTO dto = new EquationDTO();
        dto.setRoot("1.0");

        assertEquals("1.0", service.verify(dto));
    }

}
