package com.app.common.enumeration;

public enum Exception {
    USER_NOT_FOUND("User does not exist"), PASSWORD_INCORRECT("This password is not correct");

    private final String value;

    Exception(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}