package com.lms.app.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
