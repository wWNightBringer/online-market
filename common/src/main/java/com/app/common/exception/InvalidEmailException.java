package com.app.common.exception;

public class InvalidEmailException extends RuntimeException{

    public InvalidEmailException(String message){
        super(message);
    }
}
