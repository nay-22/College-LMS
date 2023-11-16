package com.lms.app.advice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.lms.app.domain.HttpResponse;
import com.lms.app.exception.ApiException;
import com.lms.app.exception.ResourceNotFoundException;


@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<HttpResponse> handleResouceNotFoundException(ResourceNotFoundException e) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Not Found", e.getMessage());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<HttpResponse> handleApiException(ApiException e) {    
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<HttpResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", "Invalid request parameter type");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpResponse> handleValidException(MethodArgumentNotValidException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", "Missing body attribute");
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<HttpResponse> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        return buildErrorResponse(HttpStatus.CONFLICT, "SQLIntegrityConstraintViolationException", e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HttpResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid Http message", e.getMessage());
    }


    private ResponseEntity<HttpResponse> buildErrorResponse(HttpStatus status, String error, String message) {
        HttpResponse response = HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .statusCode(status.value())
                .status(status)
                // .reason(error)
                .message(message)
                .build();
        return new ResponseEntity<>(response, status);
    }

}
