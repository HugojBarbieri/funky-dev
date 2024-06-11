package com.funky.packageservice.client;

import com.funky.packageservice.config.RestTemplateConfig;
import com.funky.packageservice.dto.OrderDTO;
import com.funky.packageservice.dto.ProductDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FunkyClient {

    private final RestTemplateConfig restTemplateConfig;

    public FunkyClient(RestTemplateConfig restTemplateConfig) {
        this.restTemplateConfig = restTemplateConfig;
    }

    public List<OrderDTO> getUnpackagedOrders() {
        ResponseEntity<List<OrderDTO>> ordersDTO = restTemplateConfig.restTemplate().exchange("http://FUNKY-SERVICE:8082/funky/orders/unpackaged", HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderDTO>>(){});
        return ordersDTO.getBody();
    };

    public List<ProductDTO> getProducts() {
        ResponseEntity<List<ProductDTO>> productsDTO = restTemplateConfig.restTemplate().exchange("http://FUNKY-SERVICE:8082/funky/products", HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductDTO>>(){});
        return productsDTO.getBody();
    }
}
