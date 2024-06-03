package com.funky.packageservice.service;

import com.funky.packageservice.model.Order;
import com.funky.packageservice.repository.OrderRepository;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public boolean delete(Long orderId) {
        Optional<Order> orderToDelete= orderRepository.findById(orderId);
        if(orderToDelete.isPresent()) {
            orderRepository.delete(orderToDelete.get());
            return true;
        }

        return false;
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException(
                String.format("The id:% does not exist",id)));
    }

    public Order update(Long id, boolean packaged) {
        Optional<Order> orderUpdate = orderRepository.findById(id);
        if(orderUpdate.isPresent()) {
            Order order = orderUpdate.get();
            order.setPackaged(packaged);
            orderRepository.save(order);
            return order;
        }
        throw new NoSuchElementException(String.format("The id:% does not exist",id));
    }
}
