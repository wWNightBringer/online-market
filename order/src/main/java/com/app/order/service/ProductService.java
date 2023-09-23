package com.app.order.service;

import com.app.common.dto.CreateProductDTO;
import com.app.common.dto.ProductDTO;
import com.app.common.enumeration.Brand;
import com.app.common.enumeration.Exception;
import com.app.common.enumeration.Group;
import com.app.order.domain.Product;
import com.app.order.repository.ProductRepository;
import com.app.order.util.mapper.ProductMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.app.order.service.filter.ProductSpecification.hasBrand;
import static com.app.order.service.filter.ProductSpecification.hasGroup;
import static com.app.order.service.filter.ProductSpecification.hasPriceBetween;
import static com.app.order.service.filter.ProductSpecification.hasTitle;
import static com.app.order.util.mapper.ProductMapper.listMap;
import static com.app.order.util.mapper.ProductMapper.mapCreateProduct;
import static com.app.order.util.mapper.ProductMapper.mapProduct;
import static com.app.order.util.mapper.ProductMapper.pageMap;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final EntityManager entityManager;

    @Transactional
    public CreateProductDTO addProduct(CreateProductDTO productDTO) {
        Product product = productRepository.save(ProductMapper.mapCreateProduct(productDTO));
        return mapCreateProduct(product);
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

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProductsByGroupAndBrand(String title,
                                                          Group group,
                                                          Brand brand,
                                                          BigDecimal priceFrom,
                                                          BigDecimal priceTo) {
        Specification<Product> specification = Specification
            .where(hasTitle(title))
            .and(hasGroup(group))
            .and(hasBrand(brand))
            .and(hasPriceBetween(priceFrom, priceTo));
        List<Product> products = productRepository.findAll(specification);
        return listMap(products);
    }
}
