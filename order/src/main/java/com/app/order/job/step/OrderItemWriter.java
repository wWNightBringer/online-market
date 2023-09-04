package com.app.order.job.step;

import com.app.common.enumeration.State;
import com.app.order.domain.Order;
import com.app.order.domain.Product;
import com.app.order.job.JobDTO;
import com.app.order.repository.OrderRepository;
import com.app.order.repository.ProductRepository;
import com.app.order.service.OrderService;
import com.app.order.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderItemWriter implements ItemWriter<Map<Integer, List<JobDTO.ProductCount>>> {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Override
    public void write(Chunk<? extends Map<Integer, List<JobDTO.ProductCount>>> chunk) {
        Map<Integer, List<JobDTO.ProductCount>> map = chunk.getItems().get(0);

        map.values()
            .stream()
            .flatMap(Collection::stream)
            .forEach(this::updateProduct);

        map.keySet().forEach(this::updateOrder);
    }

    private void updateOrder(Integer orderId) {
        Order order = orderService.getOrderById(orderId);
        order.setState(State.PENDING);
        orderRepository.save(order);
    }

    private void updateProduct(JobDTO.ProductCount p) {
        Product product = productService.getProductById(p.productId());
        product.setCount(p.finalCount());
        productRepository.save(product);
    }
}
