package com.funkymonkeys.application.tiendanube.resource;

import com.funkymonkeys.application.tiendanube.dto.OrderTiendaNubeDTO;
import com.funkymonkeys.application.tiendanube.service.TiendaNubeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/funky")
public class TiendaNubeResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TiendaNubeResource.class);

    private final TiendaNubeService tiendaNubeService;

    @Autowired
    public TiendaNubeResource(TiendaNubeService tiendaNubeService) {
        this.tiendaNubeService = tiendaNubeService;
    }

    @GetMapping("/orders/unpackaged")
    public List<OrderTiendaNubeDTO> getUnpackageOrders() {
        LOGGER.info("Get all un-package orders from TiendaNube:");

        return tiendaNubeService.getUnpackagedOrders();
    }
//
//    @GetMapping("/products")
//    public List<ProductDTO> getProducts() {
//        LOGGER.info("Get all products:");
//
//        return funkyService.getProducts();
//    }
}
