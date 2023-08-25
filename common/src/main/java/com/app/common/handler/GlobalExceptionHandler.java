package com.app.common.handler;

import com.app.common.exception.InvalidEmailException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Error> handleMethodArgumentNotValidException(InvalidEmailException e) {
        return ResponseEntity.status(FORBIDDEN).body(showErrorMessage(e));
    }

    private Error showErrorMessage(Exception exception) {
        return new Error(exception.getMessage());
    }

    private record Error(String errorMessage) {
    }
}
