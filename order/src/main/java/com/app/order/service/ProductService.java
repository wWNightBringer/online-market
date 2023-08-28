package com.app.order.service;

import com.app.common.dto.CreateProductDTO;
import com.app.common.dto.ProductDTO;
import com.app.order.domain.Product;
import com.app.order.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductDTO addProduct(CreateProductDTO createProductDTO) {
        Product product = productRepository.save(buildProduct(createProductDTO));
        return getProductDTO(product);
    }

    private ProductDTO getProductDTO(Product product) {
        return new ProductDTO(
            product.getUuid(),
            product.getTitle(),
            product.getPrice(),
            product.getCount());
    }

    private Product buildProduct(CreateProductDTO createProductDTO) {
        return Product.builder()
            .uuid(createProductDTO.uuid())
            .title(createProductDTO.title())
            .price(createProductDTO.price())
            .count(createProductDTO.count())
            .build();
    }
}
