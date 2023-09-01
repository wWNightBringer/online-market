package com.app.order.job.step;

import com.app.common.enumeration.State;
import com.app.order.domain.Bucket;
import com.app.order.domain.Order;
import com.app.order.domain.Product;
import com.app.order.job.JobDTO;
import com.app.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderItemReader implements ItemReader<JobDTO> {

    private final OrderRepository orderRepository;

    @Override
    public JobDTO read() {
        List<Order> orders = orderRepository.findAllByStateIs(State.OPEN);
        List<Product> products = getProducts(orders);

        return new JobDTO(orders, products);
    }

    private List<Product> getProducts(List<Order> orders) {
        return orders.stream()
            .map(Order::getBucket)
            .map(Bucket::getProducts)
            .flatMap(Collection::stream)
            .toList();
    }
}
