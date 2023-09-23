package com.app.common.enumeration;

import com.app.common.exception.EnumNotFoundException;

public enum Brand {
    SAMSUNG("Samsung"),
    LENOVO("Lenovo"),
    LG("LG"),
    ADIDAS("Adidas"),
    NIKE("Nike"),
    PUMA("Puma"),
    APPLE("Apple"),
    TOYOTA("Toyota"),
    IKEA("IKEA");

    private final String value;

    Brand(String value) {
        this.value = value;
    }

    public static Brand fromString(String text) {
        for (Brand brand : Brand.values()) {
            if (brand.value.equalsIgnoreCase(text)) {
                return brand;
            }
        }
        throw new EnumNotFoundException(Brand.class, text);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Brand{" +
            "value='" + value + '\'' +
            '}';
    }
}
