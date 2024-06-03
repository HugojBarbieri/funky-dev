package com.funky.packageservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.funky.packageservice.model.Order;
import com.funky.packageservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    public OrderControllerTest(MockMvc mockMvc, OrderService orderService) {
        this.mockMvc = mockMvc;
        this.orderService = orderService;
    }

    @Test
    public void testSaveOrder() throws Exception {
        Order order = new Order(); // Set order details here
        when(orderService.save(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()));
    }

    @Test
    public void testDeleteOrder() throws Exception {
        when(orderService.delete(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/orders/{orderId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void testFindAll() throws Exception {
        List<Order> orders = Arrays.asList(new Order(), new Order()); // Add details if needed
        when(orderService.findAll()).thenReturn(orders);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(orders.size()));
    }

    @Test
    public void testFindById() throws Exception {
        Order order = new Order(); // Set order details here
        when(orderService.findById(anyLong())).thenReturn(order);

        mockMvc.perform(get("/orders/{orderId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()));
    }
}
