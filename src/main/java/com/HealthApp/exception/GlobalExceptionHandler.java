package com.HealthApp.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import io.jsonwebtoken.security.SignatureException;
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

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<String> handleInvalidRefreshToken (InvalidRefreshTokenException ex) {
        logger.warn("Refresh rejected: invalid refresh session");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid or expired refresh token");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        logger.error("Internal error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal server error");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwt(ExpiredJwtException ex) {
        logger.warn("Refresh rejected: token expired");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid or expired refresh token");
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<String> handleMalformedJwt(MalformedJwtException ex) {
        logger.warn("Refresh rejected: malformed JWT");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid or expired refresh token");
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<String> handleSignatureException(SignatureException ex) {
        logger.warn("Refresh rejected: invalid JWT signature");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid or expired refresh token");
    }
}
