package com.funky.funkyservice.controller;

import com.funky.funkyservice.dto.CustomerOrderDTO;
import com.funky.funkyservice.dto.OrderDTO;
import com.funky.funkyservice.dto.ProductOrderDTO;
import com.funky.funkyservice.service.FunkyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FunkyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FunkyService funkyService;

    @InjectMocks
    private FunkyController funkyController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(funkyController).build();
    }

    @Test
    public void testGetUnpackageOrders() throws Exception {
        // Mocked data
        OrderDTO order1 = buildOrderDTO();
        OrderDTO order2 = buildOrderDTO2();
        List<OrderDTO> orders = Arrays.asList(order2, order1);

        // Mocking service method
        when(funkyService.getUnpackagedOrders()).thenReturn(orders);

        // Perform GET request
        mockMvc.perform(get("/funky/orders/unpackaged")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].products[0].name").value("Product 1"))
                .andExpect(jsonPath("$[0].products[0].quantity").value(2))
                .andExpect(jsonPath("$[0].products[1].name").value("Product 2"))
                .andExpect(jsonPath("$[0].products[1].quantity").value(3))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].products[0].name").value("Product Name"))
                .andExpect(jsonPath("$[1].products[0].quantity").value(2));
    }

    public OrderDTO buildOrderDTO() {
        return OrderDTO.builder()
                .id(2)
                .contactEmail("example2@example.com")
                .contactName("Pepe Doe")
                .contactPhone("123456789")
                .number(1001)
                .shippingStatus("Pending")
                .paymentStatus("Unpaid")
                .createdAt(new Date())
                .note("Some notes")
                .ownerNote("Owner's notes")
                .customer(CustomerOrderDTO.builder().name("Customer Name").build())
                .products(List.of(ProductOrderDTO.builder()
                        .id(1)
                        .depth("10cm")
                        .height("20cm")
                        .name("Product Name")
                        .quantity(2)
                        .sku("sku-1")
                        .variantValues(List.of("Variant 1", "Variant 2"))
                        .build()))
                .build();
    }

    public OrderDTO buildOrderDTO2() {
        return OrderDTO.builder()
                .id(1)
                .contactEmail("example@example.com")
                .contactName("John Doe")
                .contactPhone("123-456-7890")
                .customer(CustomerOrderDTO.builder()
                        .name("Customer Name")
                        .build())
                .products(List.of(
                        ProductOrderDTO.builder()
                                .id(1L)
                                .name("Product 1")
                                .quantity(2)
                                .sku("sku-1")
                                .depth("10.0")
                                .build(),
                        ProductOrderDTO.builder()
                                .id(2L)
                                .name("Product 2")
                                .quantity(3)
                                .sku("sku-2")
                                .depth("20.0")
                                .build()))
                .number(12345)
                .shippingStatus("Shipped")
                .paymentStatus("Paid")
                .createdAt(new Date())
                .note("This is a note")
                .ownerNote("Owner's note")
                .build();
    }
}
