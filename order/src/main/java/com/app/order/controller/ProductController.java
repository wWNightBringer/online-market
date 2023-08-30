package com.app.order.controller;

import io.micrometer.core.annotation.Timed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    @Timed(value = "products.time", description = "Get full time after executions")
    @GetMapping
    public ResponseEntity<List<String>> getProducts() {
        return ResponseEntity.ok(List.of("Hello", "Bye", "Sky", "Arrangment"));
    }
}
