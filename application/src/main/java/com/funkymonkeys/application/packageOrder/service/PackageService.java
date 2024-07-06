package com.funkymonkeys.application.packageOrder.service;

import com.funkymonkeys.application.order.dto.OrderDTO;
import com.funkymonkeys.application.order.model.Order;
import com.funkymonkeys.application.order.service.OrderService;
import com.funkymonkeys.application.tiendanube.dto.OrderTiendaNubeDTO;
import com.funkymonkeys.application.tiendanube.service.TiendaNubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PackageService{

    private final TiendaNubeService tiendaNubeService;
    private final OrderService orderService;

    @Autowired
    public PackageService(TiendaNubeService tiendaNubeService, OrderService orderService) {
        this.tiendaNubeService = tiendaNubeService;
        this.orderService = orderService;
    }

    public Optional<List<OrderTiendaNubeDTO>> getUnpackagedOrders() {
        List<Order> orderPackaged = orderService.findByPackaged();
        List<OrderTiendaNubeDTO> ordersFromFunky = tiendaNubeService.getUnpackagedOrders();

            return  Optional.of(ordersFromFunky.stream()
                    .filter(o -> orderPackaged.stream()
                            .noneMatch(op -> op.getNumber() == o.number()
                                    && op.getTiendaNubeId().equals(o.id())))
                    .collect(Collectors.toList()));
    }
}
