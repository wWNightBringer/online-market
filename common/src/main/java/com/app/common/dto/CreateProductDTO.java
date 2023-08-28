package com.app.common.dto;

import java.math.BigDecimal;

public record CreateProductDTO(String uuid, String title, BigDecimal price, int count) {
}
