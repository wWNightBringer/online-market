package com.app.order.controller;

import com.app.common.dto.CreateOrderDTO;
import com.app.common.dto.OrderDTO;
import com.app.common.enumeration.State;
import com.app.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderDTO createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
        return orderService.createOrder(createOrderDTO.productIdsDTOS());
    }

    @GetMapping("/{state}")
    public List<OrderDTO> getAllOrdersByState(@PathVariable(name = "state") State state){
        return orderService.getAllOrdersByState(state);
    }
}
