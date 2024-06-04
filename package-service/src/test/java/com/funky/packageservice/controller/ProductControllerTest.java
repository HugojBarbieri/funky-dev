package com.funky.packageservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.funky.packageservice.model.Product;
import com.funky.packageservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private List<Product> products;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        products = productList();
    }

    @Test
    void saveProduct() throws Exception {
        Product product = products.get(0);
        when(productService.save(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.imagePath").value("path1"));
    }

    @Test
    void deleteProduct() throws Exception {
        when(productService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void findAll() throws Exception {
        when(productService.findAll()).thenReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].imagePath").value("path1"))
                .andExpect(jsonPath("$[0].orderId").value(1))
                .andExpect(jsonPath("$[0].ready").value(false))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].imagePath").value("path2"))
                .andExpect(jsonPath("$[1].orderId").value(1))
                .andExpect(jsonPath("$[1].ready").value(false))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].imagePath").value("path3"))
                .andExpect(jsonPath("$[2].orderId").value(1))
                .andExpect(jsonPath("$[2].ready").value(true))
                .andExpect(jsonPath("$[3].id").value(4))
                .andExpect(jsonPath("$[3].imagePath").value("path4"))
                .andExpect(jsonPath("$[3].orderId").value(1))
                .andExpect(jsonPath("$[3].ready").value(true));
    }

    @Test
    void findById() throws Exception {
        Product product = productList().get(0);
        when(productService.findById(1L)).thenReturn(product);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.imagePath").value("path1"))
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.ready").value(false));
    }

    @Test
    void updateProductToggle() throws Exception {
        Product product = products.get(0);
        product.setReady(true);
        when(productService.updateToggle(1L)).thenReturn(product);

        mockMvc.perform(put("/products/1/toggle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.imagePath").value("path1"))
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.ready").value(true));
    }

    private List<Product> productList() {
        return Arrays.asList(
                Product.builder()
                        .id(1L)
                        .imagePath("path1")
                        .orderId(1L)
                        .ready(false).build(),
                Product.builder()
                        .id(2L)
                        .imagePath("path2")
                        .orderId(1L)
                        .ready(false).build(),
                Product.builder()
                        .id(3L)
                        .imagePath("path3")
                        .orderId(1L)
                        .ready(true).build(),
                Product.builder()
                        .id(4L)
                        .imagePath("path4")
                        .orderId(1L)
                        .ready(true)
                        .build()
        );
    }
}
