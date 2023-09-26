package com.app.order.service.filter;

import com.app.common.enumeration.Brand;
import com.app.common.enumeration.Group;
import com.app.order.domain.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {

    private ProductSpecification() {
    }

    public static Specification<Product> hasTitle(String title) {
        if (title != null) {
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + title + "%");
        }
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    public static Specification<Product> hasGroup(Group group) {
        if (group != null) {
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("group"), group);
        }
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    public static Specification<Product> hasBrand(Brand brand) {
        if (brand != null) {
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("brand"), brand);
        }
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.conjunction();
    }


    public static Specification<Product> hasPriceBetween(BigDecimal priceFrom, BigDecimal priceTo) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            BigDecimal minPrice = priceFrom != null ? priceFrom : BigDecimal.ZERO;
            BigDecimal maxPrice = priceTo != null ? priceTo : BigDecimal.valueOf(Integer.MAX_VALUE);
            return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
        };
    }
}
