package com.funkymonkeys.application.order.resource;

import com.funkymonkeys.application.order.dto.OrderDTO;
import com.funkymonkeys.application.order.model.Order;
import com.funkymonkeys.application.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/funky/orders")
public class OrderResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderResource.class);

    private final OrderService orderService;

    @Autowired
    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public ResponseEntity<Order> saveOrder(@RequestBody OrderDTO orderDTO) {
        LOGGER.info("Calling save order endpoint");

        return ResponseEntity.ok(orderService.save(orderDTO));
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
    public ResponseEntity<?> findById(@PathVariable("orderId") Long orderId) {
        Optional<Order> order = orderService.findById(orderId);
        if(order.isPresent()) {
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> packaged(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(orderService.packaged(orderId));
    }
}
