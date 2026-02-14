package com.harsh.registration_service.exception;

public class RegistrationNotFoundException extends RuntimeException {
    public RegistrationNotFoundException(String message) {
        super(message);
    }
}