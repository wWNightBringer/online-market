package com.app.order.service;

import com.app.common.dto.CreateOrderDTO;
import com.app.common.dto.OrderDTO;
import com.app.common.enumeration.Exception;
import com.app.common.enumeration.State;
import com.app.order.client.UserClient;
import com.app.order.domain.Order;
import com.app.order.domain.Product;
import com.app.order.domain.ProductOrder;
import com.app.order.repository.OrderRepository;
import com.app.order.repository.ProductOrderRepository;
import com.app.order.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.app.order.util.SecurityUtil.getUserEmail;
import static com.app.order.util.mapper.OrderMapper.buildOrder;
import static com.app.order.util.mapper.OrderMapper.getOrderDTO;
import static com.app.order.util.mapper.OrderMapper.mapList;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductOrderRepository productOrderRepository;
    private final UserClient userClient;

    @Transactional
    public OrderDTO createOrder(List<CreateOrderDTO.ProductIdsDTO> productIds, String token) {
        List<Product> products = getProductsToOrder(productIds);

        Integer userId = userClient.getUserIdByEmail(getUserEmail(), token);
        Order order = orderRepository.save(buildOrder(productIds, products, userId));

        List<ProductOrder> productOrders = productOrderRepository.findAllByProductOrderKey_OrderId(order.getId());
        updateProductOrders(productOrders, productIds);

        return getOrderDTO(order);
    }

    public List<OrderDTO> getAllOrdersByState(State state) {
        List<Order> order = orderRepository.findAllByStateIs(state);
        return mapList(order);
    }

    @Transactional(readOnly = true)
    public Order getOrderById(Integer id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(Exception.ORDER_NOT_FOUND.getValue()));
    }

    private void updateProductOrders(List<ProductOrder> productOrders, List<CreateOrderDTO.ProductIdsDTO> productIds) {
        Map<Integer, ProductOrder> map = new HashMap<>();
        productOrders.forEach(productOrder -> map.put(productOrder.getProductOrderKey().getProduct().getId(), productOrder));

        productIds.forEach(p -> {
            ProductOrder productOrder = map.get(p.productId());
            productOrder.setProductCount(p.count());
            productOrderRepository.save(productOrder);
        });
    }

    private List<Product> getProductsToOrder(List<CreateOrderDTO.ProductIdsDTO> productIds) {
        List<Integer> ids = productIds.stream()
            .map(CreateOrderDTO.ProductIdsDTO::productId)
            .toList();
        return productRepository.findAllById(ids);
    }
}
