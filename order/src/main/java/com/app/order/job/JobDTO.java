package com.app.order.job;

import java.util.List;
import java.util.Map;

public record JobDTO(Map<Integer, List<ProductCount>> map) {

    public record ProductCount(Integer productId, Integer orderCount, Integer finalCount) {

    }
}
