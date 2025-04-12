package com.matt.exceptions;

/**
 * This exception is thrown when a data integrity violation occurs.
 * It is typically used to indicate issues such as missing or corrupted data,
 * constraint violations, or any inconsistencies in the database or data processing logic.
 */
public class DataIntegrityException extends Exception {
    public DataIntegrityException(String message) {
        super(message);
    }
}

