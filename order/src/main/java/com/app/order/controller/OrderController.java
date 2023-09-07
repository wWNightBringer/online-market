package com.app.order.controller;

import com.app.common.config.security.JwtTokenUtils;
import com.app.common.dto.CreateOrderDTO;
import com.app.common.dto.OrderDTO;
import com.app.common.enumeration.State;
import com.app.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderDTO createOrderDTO, HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            return ResponseEntity.created(URI.create("/api/v1/order"))
                .body(orderService.createOrder(createOrderDTO.productIdsDTOS(), JwtTokenUtils.getToken(header)));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("{state}")
    public List<OrderDTO> getAllOrdersByState(@PathVariable(name = "state") State state) {
        return orderService.getAllOrdersByState(state);
    }
}
