package com.app.order.repository;

import com.app.common.enumeration.State;
import com.app.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByStateIs(State state);
}
