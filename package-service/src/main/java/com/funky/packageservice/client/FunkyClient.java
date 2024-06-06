package com.funky.packageservice.client;

import com.funky.packageservice.dto.OrderDTO;
import com.funky.packageservice.dto.ProductDTO;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface FunkyClient {

    @GetExchange("/funky/orders/unpackaged")
    List<OrderDTO> getUnpackagedOrders();

    @GetExchange("/funky/products")
    List<ProductDTO> getProducts();
}
