package com.app.common.dto;

import com.app.common.enumeration.State;

import java.math.BigDecimal;

public record CreateOrderDTO(BigDecimal orderNumber, BigDecimal totalCost, int count, int userId, State state) {
}
