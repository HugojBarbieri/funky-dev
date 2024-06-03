package com.funky.packageservice.controller;

import com.funky.packageservice.model.Order;
import com.funky.packageservice.service.OrderService;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public ResponseEntity<Order> saveOrder(@RequestBody Order order) {
        LOGGER.info("Calling save order endpoint");
        return ResponseEntity.ok(orderService.save(order));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Boolean> deleteOrder(@PathVariable("orderId") Long orderId) {
        LOGGER.info(String.format("Calling delete order endpoint with id: %s", orderId));
        return ResponseEntity.ok(orderService.delete(orderId));
    }

    @GetMapping()
    public ResponseEntity<List<Order>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> findById(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(orderService.findById(orderId));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable("orderId") Long orderId, @RequestParam("packaged") boolean packaged) {
        return ResponseEntity.ok(orderService.update(orderId, packaged));
    }
}
