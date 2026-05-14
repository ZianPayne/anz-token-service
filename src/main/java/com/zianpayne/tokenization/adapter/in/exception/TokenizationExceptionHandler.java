package com.zianpayne.tokenization.adapter.in.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zianpayne.tokenization.adapter.in.rest.model.ErrorResponse;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class TokenizationExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                .body(error("BAD_REQUEST", ex.getMessage(), "Invalid request"));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error("NOT_FOUND", ex.getMessage(), "Not found"));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccess(DataAccessException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error("INTERNAL_SERVER_ERROR", ex.getMessage(), "Database error"));
    }

    private static ErrorResponse error(String code, String message, String fallback) {
        String resolved = (message == null || message.isBlank()) ? fallback : message;
        return new ErrorResponse(code, resolved);
    }
}
