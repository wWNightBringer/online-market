package com.app.order.util;

import com.app.common.dto.CreateOrderDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderUtil {

    private OrderUtil() {
    }

    public static int getTotalCount(List<CreateOrderDTO.ProductIdsDTO> productIds) {
        return productIds.stream()
            .mapToInt(CreateOrderDTO.ProductIdsDTO::count)
            .sum();
    }

    public static BigDecimal calculateTotalCost(List<CreateOrderDTO.ProductIdsDTO> productIds) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CreateOrderDTO.ProductIdsDTO productId : productIds) {
            totalPrice = totalPrice.add(new BigDecimal(productId.count()).multiply(productId.price()));
        }
        return totalPrice;
    }

    public static LocalDateTime calculateDeliveryDateByAddress(List<String> storageCities,
                                                               String deliveryCity,
                                                               LocalDateTime currentDeliveryDate) {
        if (isProductExistAtOwnCity(storageCities, deliveryCity)) {
            return currentDeliveryDate;
        }

        return currentDeliveryDate.plusDays(2);
    }

    private static boolean isProductExistAtOwnCity(List<String> storageCities, String deliveryCity) {
        return storageCities.stream()
            .allMatch(deliveryCity::equals);
    }
}
