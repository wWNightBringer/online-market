package com.app.order.service;

import com.app.common.dto.ProductDTO;
import com.app.common.enumeration.Exception;
import com.app.order.domain.Product;
import com.app.order.repository.ProductRepository;
import com.app.order.util.mapper.ProductMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.app.order.util.mapper.ProductMapper.mapProduct;
import static com.app.order.util.mapper.ProductMapper.pageMap;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = productRepository.save(ProductMapper.mapProduct(productDTO));
        return mapProduct(product);
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductByTitle(String title) {
        return mapProduct(productRepository.findProductByTitle(title));
    }

    @Transactional(readOnly = true)
    public Product getProductById(Integer id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(Exception.PRODUCT_NOT_FOUND.getValue()));
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
