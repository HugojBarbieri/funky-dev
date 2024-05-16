package com.funky.funkyservice.service;

import com.funky.funkyservice.dto.OrderDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Value;


class TiendaNubeServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private TiendaNubeService service;

    @Captor
    private ArgumentCaptor<HttpEntity<String>> entityCaptor;

    @BeforeEach
    public void setup() throws URISyntaxException {
        MockitoAnnotations.openMocks(this);
        String baseUrl = "http://example.com";
        String authToken = "sampleAuthToken";
        String userAgent = "sampleUserAgent";
        service = new TiendaNubeService(baseUrl, authToken, userAgent);
    }

    @Test
    public void testGetUnpackagedOrders() throws URISyntaxException {
        // Mock ResponseEntity
        ResponseEntity<OrderDTO[]> mockResponse = ResponseEntity.ok(new OrderDTO[0]);

        // Stubbing RestTemplate.exchange method
        when(restTemplate.exchange(
                eq("http://example.com?shipping_status=unpacked&status=open"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(OrderDTO[].class)))
                .thenReturn(mockResponse);

        // Call the method
        service.getUnpackagedOrders();

        // Verify RestTemplate was called with correct parameters
        HttpHeaders capturedHeaders = entityCaptor.getValue().getHeaders();
        assertEquals(MediaType.APPLICATION_JSON, capturedHeaders.getContentType());
        assertEquals("sampleUserAgent", capturedHeaders.getFirst("User-Agent"));
        assertEquals("bearer sampleAuthToken", capturedHeaders.getFirst("Authentication"));

        // Add more assertions based on the expected behavior
        // Verify other interactions or outcomes if needed
        verify(restTemplate).exchange(
                eq(new URI("http://example.com")),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(OrderDTO[].class));
    }
}