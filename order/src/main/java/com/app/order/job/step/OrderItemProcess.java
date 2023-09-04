package com.app.order.job.step;

import com.app.common.enumeration.Exception;
import com.app.order.domain.Product;
import com.app.order.job.JobDTO;
import com.app.order.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderItemProcess implements ItemProcessor<JobDTO, Map<Integer, List<JobDTO.ProductCount>>> {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    @Override
    public Map<Integer, List<JobDTO.ProductCount>> process(JobDTO jobDTO) {
        final Map<Integer, List<JobDTO.ProductCount>> map = new HashMap<>();

        jobDTO.map()
            .forEach((key, value) -> {
                List<JobDTO.ProductCount> products = value
                    .stream()
                    .map(this::updateProduct)
                    .toList();
                map.put(key, products);
            });

        return map;
    }

    private JobDTO.ProductCount updateProduct(JobDTO.ProductCount productCount) {
        Product product = productRepository.findById(productCount.productId())
            .orElseThrow(() -> new EntityNotFoundException(Exception.USER_NOT_FOUND.name()));

        int finalCount = product.getCount() - productCount.orderCount();
        return new JobDTO.ProductCount(product.getId(), productCount.orderCount(), finalCount);
    }
}
