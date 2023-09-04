package com.app.order.service;

import com.app.common.enumeration.Exception;
import com.app.order.domain.Order;
import com.app.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public Order getOrderById(Integer id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(Exception.ORDER_NOT_FOUND.getValue()));
    }
}
