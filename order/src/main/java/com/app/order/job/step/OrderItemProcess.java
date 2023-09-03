package com.app.order.job.step;

import com.app.order.domain.Order;
import com.app.order.job.JobDTO;
import com.app.order.repository.OrderRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemProcess implements ItemProcessor<JobDTO, JobDTO> {

    private final OrderRepository orderRepository;

    public OrderItemProcess(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public JobDTO process(JobDTO jobDTO) throws Exception {

        List<Order> orders = jobDTO.orders();
        return jobDTO;
    }
}
