package com.app.common.enumeration;

import com.app.common.exception.EnumNotFoundException;

public enum Group {
    HOUSE_ELECTRONICS("House electronics"),
    CLOTH("Cloth"),
    CAR("Car"),
    ELECTRONICS("Electronics"),
    FURNITURE("Furniture");

    private final String value;

    Group(String value) {
        this.value = value;
    }

    public static Group fromString(String text) {
        for (Group group : Group.values()) {
            if (group.value.equalsIgnoreCase(text)) {
                return group;
            }
        }
        throw new EnumNotFoundException(Brand.class, text);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Group{" +
            "value='" + value + '\'' +
            '}';
    }
}
