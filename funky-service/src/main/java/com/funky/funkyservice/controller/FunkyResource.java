package com.funky.funkyservice.controller;

import com.funky.funkyservice.dto.OrderDTO;
import com.funky.funkyservice.dto.ProductDTO;
import com.funky.funkyservice.service.FunkyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/funky")
public class FunkyResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(FunkyResource.class);

    private final FunkyService funkyService;

    @Autowired
    public FunkyResource(FunkyService funkyService) {
        this.funkyService = funkyService;
    }

    @GetMapping("/orders/unpackaged")
    public List<OrderDTO> getUnpackageOrders() {
        LOGGER.info("Get all un-package orders:");

        return funkyService.getUnpackagedOrders();
    }

    @GetMapping("/products")
    public List<ProductDTO> getProducts() {
        LOGGER.info("Get all products:");

        return funkyService.getProducts();
    }
}
