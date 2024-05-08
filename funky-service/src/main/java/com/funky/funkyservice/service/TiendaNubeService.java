package com.funky.funkyservice.service;

import com.funky.funkyservice.model.OrderDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TiendaNubeService {

    private static final String BASE_URL = "http";
    private static final String AUTH_TOKEN = "123456"; // Replace with your actual authentication token


    public OrderDTO[] getUnpackagedOrders() {
        RestTemplate restTemplate = new RestTemplate();

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("User-Agent", "funky_prod (email)");
        headers.set("Authentication", "bearer " + AUTH_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make GET request
        ResponseEntity<OrderDTO[]> response = restTemplate.exchange(
                BASE_URL + "?shipping_status=unpacked&status=open",
                HttpMethod.GET,
                entity,
                OrderDTO[].class
        );

        return response.getBody();
    }
}
