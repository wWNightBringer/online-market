package com.app.order.controller;

import com.app.common.dto.OrderDTO;
import com.app.common.dto.ProductDTO;
import com.app.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderDTO createOrder(@RequestBody ProductDTO productDTO){
        return orderService.createOrder(productDTO);
    }
}
