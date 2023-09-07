package com.app.order.util.mapper;

import com.app.common.dto.CreateOrderDTO;
import com.app.common.dto.OrderDTO;
import com.app.common.enumeration.State;
import com.app.order.domain.Order;
import com.app.order.domain.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static com.app.order.util.OrderUtil.calculateTotalCost;
import static com.app.order.util.OrderUtil.getTotalCount;

public class OrderMapper {

    private OrderMapper(){}

    public static Order buildOrder(List<CreateOrderDTO.ProductIdsDTO> productIds, List<Product> products, Integer userId) {
        return Order.builder()
            .uuid(UUID.randomUUID().toString())
            .orderNumber(BigDecimal.valueOf(new Random().nextLong(100000, 10000000)))
            .deliveryDate(LocalDateTime.now().plusDays(7))
            .totalCost(calculateTotalCost(productIds))
            .totalCount(getTotalCount(productIds))
            .userId(userId)
            .products(products)
            .state(State.OPEN)
            .build();
    }

    public static List<OrderDTO> mapList(List<Order> orders) {
        return orders.stream()
            .map(OrderMapper::getOrderDTO)
            .toList();
    }

    public static OrderDTO getOrderDTO(Order order) {
        return new OrderDTO(
            order.getOrderNumber(),
            order.getDeliveryDate(),
            order.getTotalCost(),
            order.getTotalCount(),
            order.getUserId(),
            order.getState(),
            ProductMapper.listMap(order.getProducts())
        );
    }
}
