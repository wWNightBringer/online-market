package com.app.common.handler;

import com.app.common.exception.InvalidEmailException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(InvalidEmailException e) {
        return ResponseEntity.status(NOT_FOUND).body(e.getLocalizedMessage());
    }
}
