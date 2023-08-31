package com.app.order.repository;

import com.app.order.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findProductByTitle(String title);

    @Modifying
    @Query("UPDATE Product p SET p.isDeleted = true WHERE p.id=:id")
    void deleteProductById(@Param("id") Integer id);
}
