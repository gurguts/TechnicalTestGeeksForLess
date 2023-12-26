package com.example.technicaltestgeeksforless.exceptions;

/**
 * Custom exception for cases where a root is already saved in the database.
 */
public class AlreadySavedRootException extends RuntimeException {
    public AlreadySavedRootException(String message) {
        super(message);
    }
}
