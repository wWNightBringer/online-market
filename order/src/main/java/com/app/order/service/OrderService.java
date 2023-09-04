package com.app.order.service;

import com.app.common.dto.OrderDTO;
import com.app.common.dto.ProductDTO;
import com.app.common.enumeration.State;
import com.app.order.domain.Order;
import com.app.order.domain.Product;
import com.app.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.app.order.util.mapper.ProductMapper.mapProduct;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductService productService;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderDTO createOrder(ProductDTO productDTO) {
        Order order = orderRepository.save(mapOrder(productDTO));
        return getOrderDTO(order);
    }

    private Order mapOrder(ProductDTO productDTO) {
        return Order.builder()
            .uuid(UUID.randomUUID().toString())
            .orderNumber(BigDecimal.valueOf(new Random().nextDouble()))
            .deliveryDate(LocalDateTime.now().plusDays(7))
//            .totalCost(getTotalPrice(List.of(mapProduct(productDTO))))
//            .count(productService.takeProductCountToOrder(productDTO.title()))
            .products(List.of(mapProduct(productDTO)))
            .userId(7)
            .state(State.PENDING)
            .build();
    }

    private OrderDTO getOrderDTO(Order order) {
        return new OrderDTO(
            order.getOrderNumber(),
            order.getDeliveryDate(),
            order.getTotalCost(),
            order.getCount(),
            order.getUserId(),
            order.getState()
        );
    }

    private BigDecimal getTotalPrice(List<Product> products) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Product product : products) {
            if (product != null) {
                totalPrice = totalPrice.add(product.getPrice());
            }
        }
        return totalPrice;
    }
}
