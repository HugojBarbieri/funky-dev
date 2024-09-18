package com.funkymonkeys.application.tiendanube.service;

import com.funkymonkeys.application.order.model.PaymentStatus;
import com.funkymonkeys.application.tiendanube.dto.OrderTiendaNubeDTO;
import com.funkymonkeys.application.tiendanube.dto.ProductTiendaNubeDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<OrderTiendaNubeDTO> getUnpackagedOrders() {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Set headers
            HttpEntity<String> entity = getHttpEntity();
            // TODO looks like is responding with 404 when no open
            ResponseEntity<OrderTiendaNubeDTO[]> response = restTemplate.exchange(
                    baseUrl + "/orders?shipping_status=unpacked&status=open",
                    HttpMethod.GET,
                    entity,
                    OrderTiendaNubeDTO[].class
            );
            return Arrays.stream(Objects.requireNonNull(response.getBody()))
                    .filter(orderTiendaNubeDTO -> PaymentStatus.PAID.getName()
                            .equals(orderTiendaNubeDTO.getPaymentStatus()))
                    .sorted(Comparator.comparing(OrderTiendaNubeDTO::getCreatedAt)
                            .reversed()
                            .thenComparing(OrderTiendaNubeDTO::getNumber))
                                        .collect(Collectors.toList());
        } catch (NullPointerException e) {
            //TODO add log when error happens

        }
        return new ArrayList<>();
    }

    public List<ProductTiendaNubeDTO> getProducts() {
        try{
            RestTemplate restTemplate = new RestTemplate();

            // Set headers
            HttpEntity<String> entity = getHttpEntity();

            // Make GET request
            ResponseEntity<ProductTiendaNubeDTO[]> response = restTemplate.exchange(
                    baseUrl + "/products",
                    HttpMethod.GET,
                    entity,
                    ProductTiendaNubeDTO[].class
            );

            return Arrays.stream(Objects.requireNonNull(response.getBody())).collect(Collectors.toList());
        }
        catch (NullPointerException e) {
            //TODO add log when error happens
        }
        return new ArrayList<>();

    }

    private HttpEntity<String> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("User-Agent", userAgent);
        headers.set("Authentication", "bearer " + authToken);
        return new HttpEntity<>(headers);
    }
}
