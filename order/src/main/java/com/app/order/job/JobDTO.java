package com.app.order.job;

import com.app.order.domain.Order;
import com.app.order.domain.Product;

import java.util.List;

public record JobDTO(List<Order> orders, List<Product> products) {
}
