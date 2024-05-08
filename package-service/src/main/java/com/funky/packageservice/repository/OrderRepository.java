package com.funky.packageservice.repository;

import com.funky.packageservice.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {

    private List<Order> orders = new ArrayList<>();

    public Order add(Order order) {
        orders.add(order);
        return order;
    }

    public Order findById(Long id) {
        return orders.stream().filter(o -> id.equals(o.getId()))
                .findFirst()
                .orElseThrow();
    }

    public List<Order> findAll() {
        return orders;
    }
}
