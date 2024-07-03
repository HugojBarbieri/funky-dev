package com.funky.packageservice.client;

import com.funky.packageservice.dto.OrderDTO;
import com.funky.packageservice.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "funky-service", url = "${funky-service.url}")
public interface FunkyClient {

    @GetMapping("/funky/orders/unpackaged")
    List<OrderDTO> getUnpackagedOrders();

    @GetMapping("/funky/products")
    List<ProductDTO> getProducts();
}
