package com.app.common.handler;

import com.app.common.exception.InvalidEmailException;
import com.app.common.exception.InvalidStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Error> handleEmailNotValidException(RuntimeException runtimeException) {
        return ResponseEntity.status(NOT_FOUND).body(showErrorMessage(NOT_FOUND, runtimeException));
    }

    @ExceptionHandler(InvalidStateException.class)
    public ResponseEntity<Error> handleStateNotValidException(RuntimeException runtimeException) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(showErrorMessage(INTERNAL_SERVER_ERROR, runtimeException));
    }

    public Error showErrorMessage(HttpStatus status, RuntimeException runtimeException) {
        return new Error(status, runtimeException.getMessage());
    }

    private record Error(HttpStatus status, String message) {

    }
}
