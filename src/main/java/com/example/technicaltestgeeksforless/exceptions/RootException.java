package com.example.technicaltestgeeksforless.exceptions;

/**
 * Custom exception for handling errors specific to root processing.
 */
public class RootException extends RuntimeException {
    public RootException(String message) {
        super(message);
    }
}
