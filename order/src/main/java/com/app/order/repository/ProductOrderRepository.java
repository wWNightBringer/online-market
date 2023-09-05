package com.app.order.repository;

import com.app.order.domain.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, ProductOrder.ProductOrderKey> {

}
