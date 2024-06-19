package com.funky.packageservice.service;

import com.funky.packageservice.dto.OrderDTO;
import com.funky.packageservice.model.Order;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PackageService{

    private final FunkyUtils funkyUtils;
    private final OrderService orderService;

    @Autowired
    public PackageService(FunkyUtils funkyUtils, OrderService orderService) {
        this.funkyUtils = funkyUtils;
        this.orderService = orderService;
    }

    @RateLimiter(name = "funkyBreaker", fallbackMethod = "funkyBreakerFallBack")
    public Optional<List<OrderDTO>> getUnpackagedOrders() {
        List<Order> orderPackaged = orderService.findByPackaged();
        List<OrderDTO> ordersFromFunky = funkyUtils.getUnpackagedAndPaidOrders();

            return  Optional.of(ordersFromFunky.stream()
                    .filter(o -> orderPackaged.stream()
                            .noneMatch(op -> op.getNumber() == o.getNumber()
                                    && op.getTiendaNubeId().equals(o.getId())))
                    .collect(Collectors.toList()));
    }

    public  Optional<List<String>> funkyBreakerFallBack(Exception e) {
        return Optional.of(List.of("Dummy"));
    }
}
