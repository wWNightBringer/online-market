package com.app.order.util.mapper;

import com.app.common.dto.CreateOrderDTO;
import com.app.common.dto.OrderDTO;
import com.app.common.enumeration.State;
import com.app.order.domain.Order;
import com.app.order.domain.Product;
import com.app.order.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static com.app.order.util.OrderUtil.getTotalCount;
import static com.app.order.util.OrderUtil.getTotalPrice;

public class OrderMapper {

    private OrderMapper(){}



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
