package com.app.order.repository;

import com.app.order.domain.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, ProductOrder.ProductOrderKey> {

    List<ProductOrder> findAllByProductOrderKey_OrderId(Integer orderId);
}
