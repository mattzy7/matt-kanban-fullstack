package com.matt.exceptions;

/**
 * This exception is thrown when an attempt is made to create or register a user
 * that already exists in the system. It is typically used to enforce unique
 * constraints on user-related operations, such as registration or account creation.
 */
public class UserDuplicateException extends Exception {
    public UserDuplicateException(String message) {
        super(message);
    }
}
