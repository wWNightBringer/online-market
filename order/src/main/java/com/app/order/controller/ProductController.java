package com.app.order.controller;

import com.app.common.dto.CreateProductDTO;
import com.app.common.dto.ProductDTO;
import com.app.common.dto.ResponsePage;
import com.app.order.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProductDTO addProduct(@RequestBody CreateProductDTO createProductDTO) {
        return productService.addProduct(createProductDTO);
    }

    @GetMapping("/{title}")
    public ProductDTO getProductByTitle(@PathVariable(name = "title") String title) {
        return productService.getProductByTitle(title);
    }

    @DeleteMapping("/{title}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteProduct(@PathVariable(name = "title") String title) {
        productService.deleteProduct(title);
    }

    @GetMapping
    public ResponsePage<ProductDTO> getAllProducts(Pageable pageable) {
        List<ProductDTO> allProducts = productService.getAllProducts(pageable);
        return new ResponsePage<>(allProducts, allProducts.size());
    }
}
