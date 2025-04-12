package com.matt.exceptions;

/**
 * This exception is thrown when authentication fails due to invalid credentials.
 */
public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
