package com.app.order.service;

import com.app.common.dto.CreateProductDTO;
import com.app.common.dto.ProductDTO;
import com.app.order.domain.Product;
import com.app.order.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static com.app.order.util.mapper.OrderMapper.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductDTO addProduct(CreateProductDTO createProductDTO) {
        Product product = productRepository.save(createMap(createProductDTO));
        return map(product);
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductByTitle(String title) {
        return map(productRepository.findProductByTitle(title));
    }

    @Transactional
    public void deleteProduct(String title) {
        Product product = productRepository.findProductByTitle(title);
        productRepository.deleteProductById(product.getId());
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return pageMap(productPage);
    }
}
