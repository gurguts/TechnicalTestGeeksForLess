package com.example.technicaltestgeeksforless.exceptions;

/**
 * Custom exception for cases where an equation is already saved in the database.
 */
public class AlreadySavedEquationException extends RuntimeException {
    public AlreadySavedEquationException(String message) {
        super(message);
    }
}
