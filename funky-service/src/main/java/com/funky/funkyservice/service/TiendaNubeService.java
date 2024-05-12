package com.funky.funkyservice.service;

import com.funky.funkyservice.model.OrderDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TiendaNubeService {


    private String baseUrl;
    private String authToken;
    private String userAgent;

    public TiendaNubeService(@Value("${configtn.base-url}") String baseUrl,
                             @Value("${configtn.auth-token}") String authToken,
                             @Value("${configtn.user-agent}") String userAgent) {
        this.baseUrl = baseUrl;
        this.authToken = authToken;
        this.userAgent = userAgent;
    }

    public OrderDTO[] getUnpackagedOrders() {
        RestTemplate restTemplate = new RestTemplate();

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("User-Agent", userAgent);
        headers.set("Authentication", "bearer " + authToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make GET request
        ResponseEntity<OrderDTO[]> response = restTemplate.exchange(
                baseUrl + "?shipping_status=unpacked&status=open",
                HttpMethod.GET,
                entity,
                OrderDTO[].class
        );

        return response.getBody();
    }
}
