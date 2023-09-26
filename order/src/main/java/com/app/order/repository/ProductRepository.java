package com.app.order.repository;

import com.app.order.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    Product findProductByTitle(String title);

    @Modifying
    @Query("UPDATE Product p SET p.isDeleted = true WHERE p.id=:id")
    void deleteProductById(@Param("id") Integer id);
}
