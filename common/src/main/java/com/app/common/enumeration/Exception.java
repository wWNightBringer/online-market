package com.app.common.enumeration;

public enum Exception {
    USER_NOT_FOUND("User does not exist"), PASSWORD_INCORRECT("This password is not correct"),
    PRODUCT_NOT_FOUND("Product does not exist"), ORDER_NOT_FOUND("Order does not exist"),
    STORAGE_NOT_FOUND("Storage does not exist");

    private final String value;

    Exception(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
