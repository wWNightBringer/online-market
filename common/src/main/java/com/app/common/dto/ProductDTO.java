package com.app.common.dto;

import com.app.common.enumeration.Brand;
import com.app.common.enumeration.Group;

import java.math.BigDecimal;

public record ProductDTO(String title, Group group, Brand brand, BigDecimal price, int count) {
}
