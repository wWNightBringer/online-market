package com.app.order.service;

import com.app.common.dto.ProductDTO;
import com.app.order.SpringBootApplicationTest;
import com.app.order.domain.Product;
import com.app.order.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.app.common.enumeration.Brand.LENOVO;
import static com.app.common.enumeration.Brand.SAMSUNG;
import static com.app.common.enumeration.Group.ELECTRONICS;


class ProductServiceTest extends SpringBootApplicationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    private void init() {
        saveProducts();
    }

    private void saveProducts() {
        Product product1 = new Product();
        product1.setTitle("product1");
        product1.setGroup(ELECTRONICS);
        product1.setBrand(LENOVO);

        Product product2 = new Product();
        product2.setTitle("product2");
        product2.setGroup(ELECTRONICS);
        product2.setBrand(SAMSUNG);

        productRepository.saveAll(List.of(product1, product2));
    }

    @Test
    void getProductsByBrand() {
        List<ProductDTO> allProducts = productService.getAllProducts(null, null, LENOVO, null, null, Pageable.unpaged());

        allProducts.forEach(p -> Assertions.assertEquals(LENOVO, p.brand()));
    }
}
