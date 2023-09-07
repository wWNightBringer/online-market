package com.app.order.controller;

import com.app.common.dto.ProductDTO;
import com.app.common.dto.ResponsePage;
import com.app.order.service.ProductService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Timed(value = "create.products.time", description = "Create product time")
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    @Timed(value = "get.product.time", description = "Get product time after executions")
    @GetMapping("{title}")
    public ProductDTO getProductByTitle(@PathVariable(name = "title") String title) {
        return productService.getProductByTitle(title);
    }

    @DeleteMapping("{title}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteProduct(@PathVariable(name = "title") String title) {
        productService.deleteProduct(title);
    }

    @Timed(value = "get.products.time", description = "Get all products time after executions")
    @GetMapping
    public ResponsePage<ProductDTO> getAllProducts(Pageable pageable) {
        List<ProductDTO> allProducts = productService.getAllProducts(pageable);
        return new ResponsePage<>(allProducts, allProducts.size());
    }
}
