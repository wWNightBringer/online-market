package com.app.common.exception;

public class EnumNotFoundException extends RuntimeException {
    private final Class<?> type;
    private final String error;

    public EnumNotFoundException(Class<?> type, String error) {
        this.type = type;
        this.error = error;
    }

    public Class<?> getType() {
        return type;
    }

    public String getError() {
        return error;
    }
}
