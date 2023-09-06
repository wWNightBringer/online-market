package com.app.order.util;

import com.app.common.dto.CreateOrderDTO;

import java.math.BigDecimal;
import java.util.List;

public class OrderUtil {

    public static int getTotalCount(List<CreateOrderDTO.ProductIdsDTO> productIds) {
        return productIds.stream()
            .mapToInt(CreateOrderDTO.ProductIdsDTO::count).sum();
    }

    public static BigDecimal getTotalPrice(List<CreateOrderDTO.ProductIdsDTO> productIds) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CreateOrderDTO.ProductIdsDTO productId : productIds) {
            totalPrice = totalPrice.add(new BigDecimal(productId.count()).multiply(productId.price()));
        }
        return totalPrice;
    }
}
