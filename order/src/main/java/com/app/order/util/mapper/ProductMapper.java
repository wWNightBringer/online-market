package com.app.order.util.mapper;

import com.app.common.dto.ProductDTO;
import com.app.order.domain.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public class ProductMapper {

    private ProductMapper(){}

    public static ProductDTO mapProduct(Product product) {
        return new ProductDTO(
            product.getTitle(),
            product.getGroup(),
            product.getBrand(),
            product.getPrice(),
            product.getCount());
    }

    public static List<ProductDTO> pageMap(Page<Product> products) {
        return products.getContent().stream()
            .map(ProductMapper::mapProduct)
            .toList();
    }

    public static Product mapProduct(ProductDTO productDTO) {
        return Product.builder()
            .uuid(UUID.randomUUID().toString())
            .title(productDTO.title())
            .group(productDTO.group())
            .brand(productDTO.brand())
            .price(productDTO.price())
            .count(productDTO.count())
            .build();
    }
}
