package com.app.common.enumeration;

import com.app.common.exception.EnumNotFoundException;

public enum Role {
    ADMIN("ADMIN"), CUSTOMER("CUSTOMER"), ANONYMOUS("ANONYMOUS");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public static Role fromString(String text) {
        for (Role role : Role.values()) {
            if (role.value.equalsIgnoreCase(text)) {
                return role;
            }
        }
        throw new EnumNotFoundException(Role.class, text);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
