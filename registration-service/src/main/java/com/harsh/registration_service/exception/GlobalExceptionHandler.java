package com.harsh.registration_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUser(InvalidUserException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidEventException.class)
    public ResponseEntity<ErrorResponse> handleInvalidEvent(InvalidEventException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(RegistrationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRegNotFound(RegistrationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getMessage(), LocalDateTime.now()));
    }
}

