package com.app.common.dto;

import java.math.BigDecimal;

public record ProductDTO(String title, String uuid, BigDecimal price, int count) {
}
