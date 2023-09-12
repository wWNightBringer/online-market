package com.app.common.handler;

import com.app.common.exception.InvalidEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Error> handleMethodArgumentNotValidException(InvalidEmailException emailException) {
        return ResponseEntity.status(NOT_FOUND).body(showErrorMessage(NOT_FOUND, emailException));
    }

    public Error showErrorMessage(HttpStatus status, RuntimeException runtimeException) {
        return new Error(status, runtimeException.getMessage());
    }

    private record Error(HttpStatus status, String message) {

    }
}
