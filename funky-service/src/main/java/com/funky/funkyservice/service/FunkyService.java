package com.funky.funkyservice.service;

import com.funky.funkyservice.dto.OrderDTO;
import com.funky.funkyservice.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FunkyService {

    private final TiendaNubeService tiendaNubeService;

    @Autowired
    public FunkyService(TiendaNubeService tiendaNubeService) {
        this.tiendaNubeService = tiendaNubeService;
    }

    public List<OrderDTO> getUnpackagedOrders() {
        List<OrderDTO> unpackagedOrders;
        try {
            unpackagedOrders = Arrays.asList(tiendaNubeService.getUnpackagedOrders());
        } catch (Exception e) {
            //TODO add log when error happens
            return new ArrayList<>();
        }

        return unpackagedOrders.stream().sorted(Comparator.comparing(OrderDTO::createdAt).reversed().thenComparing(OrderDTO::number)).collect(Collectors.toList());
    }

    public List<ProductDTO> getProducts() {
        return Arrays.asList(tiendaNubeService.getProducts());
    }
}
