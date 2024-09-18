package com.funkymonkeys.application.order.repository;

import com.funkymonkeys.application.order.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@SpringJUnitConfig
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        // Set up test data
        Product product1 = Product.builder()
                .imagePath("path1")
                .name("Product 1")
                .sku("SKU1")
                .ready(true)
                .orderId(1L)
                .orderNumber(100)
                .imageUrl("url1")
                .build();

        Product product2 = Product.builder()
                .imagePath("path2")
                .name("Product 2")
                .sku("SKU2")
                .ready(false)
                .orderId(2L)
                .orderNumber(100)
                .imageUrl("url2")
                .build();

        Product product3 = Product.builder()
                .imagePath("path3")
                .name("Product 3")
                .sku("SKU3")
                .ready(true)
                .orderId(3L)
                .orderNumber(200)
                .imageUrl("url3")
                .build();

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
    }

    @Test
    public void testFindByOrderNumber() {
        List<Product> products = productRepository.findByOrderNumber(100);
        assertThat(products).hasSize(2);
        assertThat(products).extracting(Product::getName).containsExactlyInAnyOrder("Product 1", "Product 2");
    }
}