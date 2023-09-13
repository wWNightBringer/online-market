package com.app.common.enumeration;

import com.app.common.exception.EnumNotFoundException;
import lombok.Getter;

@Getter
public enum City {
    DNIPRO("Dnipro"), KYIV("Kyiv"), LVIV("Lviv");

    private final String value;

    City(String value) {
        this.value = value;
    }

    public static City fromString(String text) {
        for (City city : City.values()) {
            if (city.value.equalsIgnoreCase(text)) {
                return city;
            }
        }
        throw new EnumNotFoundException(Brand.class, text);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
