package com.app.order.service.listener;

import com.app.order.domain.Product;
import jakarta.persistence.PreUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductListener {

    private static final Logger log = LoggerFactory.getLogger(ProductListener.class);

    private static final String PRODUCT_COUNT_ERROR = "Product '{}' count less than 1";

    @PreUpdate
    public void validateProduct(Product product) {
        validateProductCount(product);
    }

    private void validateProductCount(Product product) {
        if (product.getCount() < 1) {
            log.error(PRODUCT_COUNT_ERROR, product.getId());
        }
    }
}
