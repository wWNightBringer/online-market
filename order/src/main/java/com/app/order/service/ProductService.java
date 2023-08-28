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

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductDTO addProduct(CreateProductDTO createProductDTO) {
        Product product = productRepository.save(buildProduct(createProductDTO));
        return getProductDTO(product);
    }

    public ProductDTO getProductByTitle(String title) {
        return getProductDTO(productRepository.findProductByTitle(title));
    }

    @Transactional
    public void deleteProduct(String title) {
        Product product = productRepository.findProductByTitle(title);
        productRepository.deleteProductById(product.getId());
    }

    public Page<ProductDTO> getAllProducts(Pageable pageable){
        return getProductPage(productRepository.findAll(pageable));
    }

    private ProductDTO getProductDTO(Product product) {
        return new ProductDTO(
            product.getUuid(),
            product.getTitle(),
            product.getGroup(),
            product.getBrand(),
            product.getPrice(),
            product.getCount());
    }

    private Page<ProductDTO> getProductPage(Page<Product> products){
        return products.map(this::getProductDTO);
    }

    private Product buildProduct(CreateProductDTO createProductDTO) {
        return Product.builder()
            .uuid(createProductDTO.uuid())
            .title(createProductDTO.title())
            .group(createProductDTO.group())
            .brand(createProductDTO.brand())
            .price(createProductDTO.price())
            .count(createProductDTO.count())
            .build();
    }
}
