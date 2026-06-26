package com.HealthApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
* This class handles exceptions for the whole REST API
*
* Instead of letting Spring Boot return a long ugly error page/stack trace,
* this class catches errors and sends back cleaner HTTP responses.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    /*
    * Handle general runtime errors and returns a readable error message
    * Instead of exposing a full stack trace to the client
     */

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}
