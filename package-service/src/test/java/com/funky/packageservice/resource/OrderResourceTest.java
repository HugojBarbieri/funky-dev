package com.funky.packageservice.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.funky.packageservice.model.Order;
import com.funky.packageservice.model.ProductOrder;
import com.funky.packageservice.model.ShipStatus;
import com.funky.packageservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderResource.class)
public class OrderResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSaveOrder() throws Exception {
        Order order = createAMock();
        when(orderService.save(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.number").value(order.getNumber()))
                .andExpect(jsonPath("$.tiendaNubeId").value(order.getTiendaNubeId()))
                .andExpect(jsonPath("$.customer").value(order.getCustomer()))
                .andExpect(jsonPath("$.shipStatus").value(order.getShipStatus().getName().toUpperCase()))
                .andExpect(jsonPath("$.products[0].id").value(order.getProductOrders().get(0).getId()))
                .andExpect(jsonPath("$.products[0].orderId").value(order.getProductOrders().get(0).getOrderId()));
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
        List<Order> orders = Collections.singletonList(createAMock()); // Add details if needed
        when(orderService.findAll()).thenReturn(orders);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(orders.size()));
    }

    @Test
    public void testFindById() throws Exception {
        Order order = createAMock();
        when(orderService.findById(anyLong())).thenReturn(order);

        mockMvc.perform(get("/orders/{orderId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()));
    }

    private Order createAMock() {
        return Order.builder()
                .productOrders(Collections.singletonList(ProductOrder.builder()
                        .id(1L)
                        .imagePath("path1")
                        .orderId(1L)
                        .ready(false).build()))
                .id(1L)
                .number(42)
                .customer("venta")
                .packaged(false)
                .shipStatus(ShipStatus.ANDREANI)
                .tiendaNubeId(123L)
                .build();
    }
}
