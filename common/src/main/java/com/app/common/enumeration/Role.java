package com.app.common.enumeration;

public enum Role {
    ADMIN("admin"), CUSTOMER("customer"), ANONYMOUS("anonymous");

    private final String role;

    Role(String role) {
        this.role = role;
    }
}
