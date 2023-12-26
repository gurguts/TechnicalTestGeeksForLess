package com.example.technicaltestgeeksforless.exceptions;

/**
 * Custom exception for handling errors specific to equation processing.
 */
public class EquationException extends RuntimeException {
    public EquationException(String message) {
        super(message);
    }
}
