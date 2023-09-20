package com.app.order.controller;

import com.app.common.config.security.JwtTokenUtils;
import com.app.common.dto.CreateOrderDTO;
import com.app.common.dto.OrderDTO;
import com.app.common.enumeration.State;
import com.app.order.service.JobService;
import com.app.order.service.OrderService;
import io.micrometer.core.annotation.Timed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;
    private final JobService jobService;

    @Timed("create.order")
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderDTO createOrderDTO, HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            return ResponseEntity.created(URI.create("/api/v1/order"))
                .body(orderService.createOrder(createOrderDTO.productIdsDTOS(), JwtTokenUtils.getToken(header)));
        }
        return ResponseEntity.notFound().build();
    }

    @Timed("read.order.byState")
    @GetMapping("{state}")
    public List<OrderDTO> getAllOrdersByState(@PathVariable(name = "state") State state) {
        return orderService.getAllOrdersByState(state);
    }

    @Timed("confirm.order.byId")
    @PostMapping("/confirmation/{orderId}")
    public void confirmOrder(@PathVariable(name = "orderId") Integer orderId) {
        orderService.confirmOrder(orderId);
        jobService.launchJob();
    }

    @PostMapping("/cancellation/{orderId}")
    public void cancelOrder(@PathVariable(name = "orderId") Integer orderId) {
        orderService.cancelOrder(orderId);
    }
}
