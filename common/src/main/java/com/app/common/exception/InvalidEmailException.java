package com.app.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidEmailException extends RuntimeException{

    public InvalidEmailException(String message){
        super(message);
    }
}
