package com.funkymonkeys.application.order.repository;


import com.funkymonkeys.application.order.model.Order;
import com.funkymonkeys.application.order.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderStatus(OrderStatus orderStatus);
}
