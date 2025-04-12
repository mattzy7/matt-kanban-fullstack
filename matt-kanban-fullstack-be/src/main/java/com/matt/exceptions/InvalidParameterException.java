package com.matt.exceptions;

/**
 * This exception is thrown when a method receives an invalid parameter.
 * It is typically used to indicate that the input provided does not meet the
 * expected criteria or violates the method's constraints.
 */
public class InvalidParameterException extends Exception {
    public InvalidParameterException(String message) {
        super(message);
    }
}
