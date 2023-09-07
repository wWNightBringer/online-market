package com.app.common.dto;

import com.app.common.enumeration.State;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(BigDecimal orderNumber, LocalDateTime deliveryDate, BigDecimal totalCost, int totalCount, State state, List<ProductDTO> productDTOS) {
}
