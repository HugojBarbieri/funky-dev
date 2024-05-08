package com.funky.packageservice.controller;

import com.funky.packageservice.model.Order;
import com.funky.packageservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @PostMapping
    public Order add(@RequestBody Order order) {
        LOGGER.info("Department added: ={}", order.getName());
        return orderRepository.add(order);
    }

    @GetMapping
    public List<Order> findAll() {
        LOGGER.info("Find all");
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public Order findById(@PathVariable Long id) {
        LOGGER.info("Find By Id={}", id);
        return orderRepository.findById(id);
    }
}
