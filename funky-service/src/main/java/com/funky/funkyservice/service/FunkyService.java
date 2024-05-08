package com.funky.funkyservice.service;

import com.funky.funkyservice.model.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FunkyService {

    private TiendaNubeService tiendaNubeService;

    @Autowired
    public FunkyService(TiendaNubeService tiendaNubeService) {
        this.tiendaNubeService = tiendaNubeService;
    }

    public List<OrderDTO> getUnpackagedOrders() {
        List<OrderDTO> orders = new ArrayList<>(Arrays.asList(tiendaNubeService.getUnpackagedOrders()));
        return orders.stream().sorted(Comparator.comparing(OrderDTO::getCreatedAt).reversed().thenComparing(OrderDTO::getNumber)).collect(Collectors.toList());
    }
}
