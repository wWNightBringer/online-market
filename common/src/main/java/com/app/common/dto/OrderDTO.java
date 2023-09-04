package com.app.common.dto;

import com.app.common.enumeration.State;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderDTO(BigDecimal orderNumber, LocalDateTime deliveryDate, BigDecimal totalCost, int count, int userId, State state) {
}
