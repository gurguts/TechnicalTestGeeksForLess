package com.example.technicaltestgeeksforless.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for Equation data, encapsulating the equation string and its root.
 */
@Data
public class EquationDTO {
    private String equation;
    private String root;
}
