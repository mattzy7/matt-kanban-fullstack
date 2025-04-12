package com.matt.exceptions;

/**
 * This exception is thrown when a requested user cannot be found in the system.
 * It is typically used to indicate that the user does not exist or has been deleted.
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}
