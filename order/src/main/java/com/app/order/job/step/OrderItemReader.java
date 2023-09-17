package com.app.order.job.step;

import com.app.common.enumeration.State;
import com.app.order.domain.Order;
import com.app.order.job.JobDTO;
import com.app.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderItemReader implements ItemReader<JobDTO> {

    private static final Logger log = LoggerFactory.getLogger(OrderItemReader.class);

    private final OrderRepository orderRepository;

    private boolean batchJobState = true;

    @Transactional(readOnly = true)
    @Override
    public JobDTO read() {
        if (batchJobState) {
            List<Order> orders = orderRepository.findAllByStateIs(State.PENDING);
            if (orders.isEmpty()) {
                return null;
            }

            log.debug("Executing product and order were started");
            Map<Integer, List<JobDTO.ProductCount>> orderMap = buildMap(orders);

            batchJobState = false;
            return new JobDTO(orderMap);
        }

        batchJobState = true;
        return null;
    }

    private Map<Integer, List<JobDTO.ProductCount>> buildMap(List<Order> orders) {
        return orders.stream()
            .collect(Collectors.toMap(Order::getId, this::buildProductCount));
    }

    private List<JobDTO.ProductCount> buildProductCount(Order order) {
        return order.getProductOrders()
            .stream()
            .map(productOrder -> new JobDTO.ProductCount(
                productOrder.getProductOrderKey().getProduct().getId(),
                productOrder.getProductCount(),
                null))
            .toList();
    }
}
