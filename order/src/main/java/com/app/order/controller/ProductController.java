package com.app.order.controller;

import com.app.common.dto.CreateProductDTO;
import com.app.common.dto.ProductDTO;
import com.app.order.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductDTO addProduct(@RequestBody CreateProductDTO createProductDTO) {
        return productService.addProduct(createProductDTO);
    }
}
