package com.funky.funkyservice.service;

import com.funky.funkyservice.dto.OrderDTO;
import com.funky.funkyservice.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class TiendaNubeService {


    private final URI baseUrl;
    private final String authToken;
    private final String userAgent;

    public TiendaNubeService(@Value("${configtn.base-url}") String baseUrl,
                             @Value("${configtn.auth-token}") String authToken,
                             @Value("${configtn.user-agent}") String userAgent) throws URISyntaxException {
        this.baseUrl = new URI(baseUrl);
        this.authToken = authToken;
        this.userAgent = userAgent;
    }

    public OrderDTO[] getUnpackagedOrders() {
        RestTemplate restTemplate = new RestTemplate();

        // Set headers
        HttpEntity<String> entity = getHttpEntity();
        // TODO looks like is responding with 404 when no open
        ResponseEntity<OrderDTO[]> response = restTemplate.exchange(
                baseUrl + "/orders?shipping_status=unpacked&status=open",
                HttpMethod.GET,
                entity,
                OrderDTO[].class
        );

        return response.getBody();
    }

    public ProductDTO[] getProducts() {
        RestTemplate restTemplate = new RestTemplate();

        // Set headers
        HttpEntity<String> entity = getHttpEntity();

        // Make GET request
        ResponseEntity<ProductDTO[]> response = restTemplate.exchange(
                baseUrl + "/products",
                HttpMethod.GET,
                entity,
                ProductDTO[].class
        );

        return response.getBody();
    }

    private HttpEntity<String> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("User-Agent", userAgent);
        headers.set("Authentication", "bearer " + authToken);
        return new HttpEntity<>(headers);
    }
}
