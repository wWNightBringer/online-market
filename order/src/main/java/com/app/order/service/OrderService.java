package com.app.order.service;

import com.app.common.dto.CreateOrderDTO;
import com.app.common.dto.OrderDTO;
import com.app.common.dto.StorageDTO;
import com.app.common.enumeration.City;
import com.app.common.enumeration.Exception;
import com.app.common.enumeration.State;
import com.app.common.exception.InvalidStateException;
import com.app.order.client.StorageClient;
import com.app.order.client.UserClient;
import com.app.order.domain.Order;
import com.app.order.domain.Product;
import com.app.order.domain.ProductOrder;
import com.app.order.repository.OrderRepository;
import com.app.order.repository.ProductOrderRepository;
import com.app.order.repository.ProductRepository;
import com.app.order.util.OrderUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.app.common.enumeration.State.CANCELLED;
import static com.app.common.enumeration.State.OPEN;
import static com.app.common.enumeration.State.PENDING;
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
    private final StorageClient storageClient;

    @Transactional
    public OrderDTO createOrder(List<CreateOrderDTO.ProductIdsDTO> productIds, String token) {
        List<Product> products = getProductsToOrder(productIds);

        Integer userId = userClient.getUserIdByEmail(getUserEmail(), token);
        Order order = orderRepository.save(buildOrder(productIds, products, userId));

        List<ProductOrder> productOrders = productOrderRepository.findAllByProductOrderKey_OrderId(order.getId());
        updateProductOrders(productOrders, productIds);

        return getOrderDTO(order);
    }

    @Transactional
    public void confirmOrder(Integer orderId) {
        Order order = getOrderById(orderId);

        if (OPEN.equals(order.getState())) {
            order.setState(PENDING);
            orderRepository.save(order);
        } else {
            throw new InvalidStateException("The order is not open");
        }
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

    @Transactional(readOnly = true)
    public LocalDateTime getDeliveryDate(Integer orderId, City deliveryCity) {
        Order order = getOrderById(orderId);

        List<Integer> productIds = order.getProducts().stream()
            .map(Product::getId)
            .toList();

        StorageDTO[] storages = storageClient.getStoragesByProductIds(productIds);

        List<String> storageCities = Arrays.stream(storages)
            .map(StorageDTO::city)
            .toList();

        return OrderUtil.calculateDeliveryDateByAddress(storageCities, deliveryCity.getValue(), order.getDeliveryDate());
    }

    @Transactional
    public void cancelOrder(Integer orderId) {
        Order order = getOrderById(orderId);
        order.setState(CANCELLED);

        List<ProductOrder> productOrders = order.getProductOrders();

        productOrders.forEach(p -> {
            Product product = p.getProductOrderKey().getProduct();
            product.setCount(product.getCount() + p.getProductCount());
        });

        orderRepository.save(order);
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
