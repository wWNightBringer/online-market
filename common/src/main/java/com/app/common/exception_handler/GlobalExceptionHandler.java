package com.app.common.exception_handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Error> handleEmailValidationException(Throwable e) {
        return ResponseEntity.status(FORBIDDEN).body(showErrorMessage(e));
    }

    private Error showErrorMessage(Throwable exception) {
        return new Error(exception.getMessage());
    }

    private record Error(String errorMessage) {
    }
}
