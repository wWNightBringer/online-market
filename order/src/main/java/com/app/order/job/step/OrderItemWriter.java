package com.app.order.job.step;

import com.app.common.enumeration.Exception;
import com.app.common.enumeration.State;
import com.app.order.domain.Order;
import com.app.order.job.JobDTO;
import com.app.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderItemWriter implements ItemWriter<Map<Integer, List<JobDTO.ProductCount>>> {

    private final OrderRepository orderRepository;

    @Override
    public void write(Chunk<? extends Map<Integer, List<JobDTO.ProductCount>>> chunk) {
        Map<Integer, List<JobDTO.ProductCount>> map = chunk.getItems().get(0);

        map.entrySet().stream()
            .filter(m -> !anyProductHasIncorrectCount(m.getValue()))
            .forEach(m -> updateOrderWithWaitingState(m.getKey(), m.getValue()));

        map.entrySet().stream()
            .filter(m -> anyProductHasIncorrectCount(m.getValue()))
            .forEach(this::updateOrderWithRejectedState);
    }

    private void updateOrderWithRejectedState(Map.Entry<Integer, List<JobDTO.ProductCount>> m) {
        Order order = findOrderById(m.getKey());
        updateOrderByState(order, State.REJECTED);
    }

    private void updateOrderWithWaitingState(Integer orderId, List<JobDTO.ProductCount> productCounts) {
        Order order = findOrderById(orderId);

        Map<Integer, Integer> productIdMap = convertToMap(productCounts);
        order.getProducts()
            .forEach(product -> {
                Integer finalCount = productIdMap.get(product.getId());
                product.setCount(finalCount);
            });

        updateOrderByState(order, State.WAITING_FOR_PACKAGING);
    }

    private Order findOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException(Exception.ORDER_NOT_FOUND.getValue()));
    }

    private void updateOrderByState(Order order, State state) {
        order.setState(state);
        orderRepository.save(order);
    }

    private Map<Integer, Integer> convertToMap(List<JobDTO.ProductCount> productCounts) {
        return productCounts.stream()
            .collect(Collectors.toMap(JobDTO.ProductCount::productId, JobDTO.ProductCount::finalCount));
    }

    private boolean anyProductHasIncorrectCount(List<JobDTO.ProductCount> productCounts) {
        return productCounts.stream()
            .anyMatch(product -> product.finalCount() < 1);
    }
}
