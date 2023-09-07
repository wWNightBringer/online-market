package com.app.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderDTO(@JsonProperty(value = "productIds") List<ProductIdsDTO> productIdsDTOS) {

    public record ProductIdsDTO(Integer productId, Integer count, BigDecimal price) {
    }
}
