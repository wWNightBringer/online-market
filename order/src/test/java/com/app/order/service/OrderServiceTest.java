package com.app.order.service;

import com.app.common.dto.StorageDTO;
import com.app.common.enumeration.City;
import com.app.common.enumeration.State;
import com.app.order.client.StorageClient;
import com.app.order.client.UserClient;
import com.app.order.domain.Order;
import com.app.order.domain.Product;
import com.app.order.repository.OrderRepository;
import com.app.order.repository.ProductOrderRepository;
import com.app.order.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {
    OrderService.class
})
@ExtendWith(SpringExtension.class)
class OrderServiceTest {

    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private ProductOrderRepository productOrderRepository;
    @MockBean
    private UserClient userClient;
    @MockBean
    private StorageClient storageClient;

    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private JobService jobService;

    @Autowired
    private OrderService orderService;

    @Test
    void getDeliveryDate() {
        Integer orderId = 1;
        City deliveryCity = City.KYIV;

        Order order = buildOrder();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(storageClient.getStoragesByProductIds(any())).thenReturn(new StorageDTO[]{new StorageDTO("address", City.DNIPRO.getValue())});

        LocalDateTime deliveryDate = orderService.getDeliveryDate(orderId, deliveryCity);
        Assertions.assertEquals(order.getDeliveryDate().plusDays(2), deliveryDate);
    }

    @Test
    void confirmOrderWithValidState() {
        Integer orderId = 1;

        Order order = buildOrder();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        orderService.confirmOrder(orderId);

        Assertions.assertEquals(State.PENDING, order.getState());
    }

    private Order buildOrder() {
        Order order = new Order();
        order.setId(1);
        order.setDeliveryDate(LocalDateTime.now());
        order.setProducts(buildProducts());
        order.setState(State.OPEN);
        return order;
    }

    private List<Product> buildProducts() {
        Product product1 = new Product();
        product1.setId(1);
        Product product2 = new Product();
        product2.setId(2);
        Product product3 = new Product();
        product3.setId(3);

        return List.of(product1, product2, product3);
    }
}
