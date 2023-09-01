package com.app.order.repository;

import com.app.common.enumeration.State;
import com.app.order.domain.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @EntityGraph(value = Order.ORDER_ENTITY_GRAPH_NAME)
    List<Order> findAllByStateIs(State state);
}
