package com.app.common.enumeration;

public enum SystemEnum {
    EMAIL_PARAM("email"), ROLE_PARAM("role");

    private final String value;

    SystemEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
