package com.matt.exceptions;

import com.matt.dto.response.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions where validation of request parameters fails.
     * This method is triggered when any validation annotations on request objects
     * annotated with @Valid fail, causing a MethodArgumentNotValidException to be thrown.
     *
     * @param ex the MethodArgumentNotValidException containing the validation errors
     * @return a ResponseEntity containing a string with all validation error messages
     *         and a 400 Bad Request status code
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder errorMessage = new StringBuilder("Validation failed: ");
        for (ObjectError error : bindingResult.getAllErrors()) {
            errorMessage.append(error.getDefaultMessage()).append("; ");
        }
        log.warn("Validation exception occurred: {}", errorMessage.toString(), ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(APIResponse.failure("Validation exception occurred: " + errorMessage.toString()));
    }

    /**
     * General exception handler for any unhandled exceptions.
     * This method is triggered when any exception is thrown that does not have a specific handler.
     *
     * @param ex the generic exception thrown by the application
     * @return a ResponseEntity with the error message and a 500 Internal Server Error status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Void>> handleGeneralException(Exception ex) {
        log.error("Unexpected exception occurred: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(APIResponse.failure("Unexpected error occurred: " + ex.getMessage()));
    }

    /**
     * Handles exceptions related to JWT errors.
     * This method is triggered by specific exceptions like NoSuchAlgorithmException or InvalidKeySpecException
     * that occur during JWT processing.
     *
     * @param ex the exception related to JWT processing errors
     * @return a ResponseEntity with the error message and a 500 Internal Server Error status
     */
    @ExceptionHandler({NoSuchAlgorithmException.class, InvalidKeySpecException.class})
    public ResponseEntity<APIResponse<Void>> handleJwtException(Exception ex) {
        log.warn("JWT error {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(APIResponse.failure("JWT error occurred: " + ex.getMessage()));
    }

    /**
     * Handles invalid parameter exceptions.
     * This method is triggered when a request contains invalid parameters.
     *
     * @param ex the InvalidParameterException that contains details about the invalid parameter
     * @return a ResponseEntity with the error message and a 400 Bad Request status
     */
    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<APIResponse<Void>> handleInvalidParameterException(Exception ex) {
        log.warn("Invalid params {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(APIResponse.failure("Bad request: " + ex.getMessage()));
    }

    /**
     * Handles user not found exceptions.
     * This method is triggered when an operation requires a user that is not found in the system.
     *
     * @param ex the UserNotFoundException containing details about the missing user
     * @return a ResponseEntity with the error message and a 404 Not Found status
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<APIResponse<Void>> handleUserNotFoundException(Exception ex) {
        log.warn("User not found {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(APIResponse.failure("User not found: " + ex.getMessage()));
    }

    /**
     * Handles invalid credentials exceptions.
     * This method is triggered when a user provides invalid credentials, e.g., incorrect username or password.
     *
     * @param ex the InvalidCredentialsException containing details about the invalid credentials
     * @return a ResponseEntity with the error message and a 401 Unauthorized status
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<APIResponse<Void>> handleInvalidCredentialException(Exception ex) {
        log.warn("Invalid credential {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(APIResponse.failure("Invalid credentials: " + ex.getMessage()));
    }

    /**
     * Handles user duplication exceptions.
     * This method is triggered when an attempt is made to create or register a user that already exists.
     *
     * @param ex the UserDuplicateException containing details about the duplicate user
     * @return a ResponseEntity with the error message and a 409 Conflict status
     */
    @ExceptionHandler(UserDuplicateException.class)
    public ResponseEntity<APIResponse<Void>> handleUserDuplicationException(Exception ex) {
        log.warn("Duplicated user {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(APIResponse.failure("Duplicated user: " + ex.getMessage()));
    }

    /**
     * Handles data integrity exceptions.
     * This method is triggered when there is a violation of data integrity (e.g., database constraints).
     *
     * @param ex the DataIntegrityException containing details about the data integrity issue
     * @return a ResponseEntity with the error message and a 500 Internal Server Error status
     */
    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<APIResponse<Void>> handleDataIntegrityException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(APIResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(EnumNotImplementedException.class)
    public ResponseEntity<APIResponse<Void>> handleEnumNotImplementedException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(APIResponse.failure(ex.getMessage()));
    }

}