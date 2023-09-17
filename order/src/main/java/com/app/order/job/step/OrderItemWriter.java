package com.app.order.job.step;

import com.app.common.enumeration.Exception;
import com.app.common.enumeration.State;
import com.app.common.exception.ProductCountException;
import com.app.order.domain.Order;
import com.app.order.domain.Product;
import com.app.order.job.JobDTO;
import com.app.order.repository.OrderRepository;
import com.app.order.repository.ProductRepository;
import com.app.order.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.app.order.service.listener.ProductListener.PRODUCT_COUNT_ERROR;

@Component
@RequiredArgsConstructor
public class OrderItemWriter implements ItemWriter<Map<Integer, List<JobDTO.ProductCount>>> {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final OrderRepository orderRepository;

    private static final Logger log = LoggerFactory.getLogger(OrderItemWriter.class);

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
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(Exception.ORDER_NOT_FOUND.getValue()));
        order.setState(State.WAITING_FOR_PACKAGING);
        orderRepository.save(order);
    }

    private void updateProduct(JobDTO.ProductCount p) {
        Product product = productService.getProductById(p.productId());
        validateProductCount(product.getCount());
        product.setCount(p.finalCount());
        productRepository.save(product);
    }

    private void validateProductCount(Integer count) {
        if (count < 1) {
            log.error(PRODUCT_COUNT_ERROR);
            throw new ProductCountException(PRODUCT_COUNT_ERROR);
        }
    }
}
