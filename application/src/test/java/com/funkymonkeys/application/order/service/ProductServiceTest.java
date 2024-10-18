package com.funkymonkeys.application.order.service;

import com.funkymonkeys.application.order.dto.BasicProductDTO;
import com.funkymonkeys.application.order.model.Product;
import com.funkymonkeys.application.order.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Disabled("This test is ignored for now")
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private BasicProductDTO basicProductDTO;

    @BeforeEach
    public void setUp() {
        product = Product.builder()
                .id(1L)
                .sku("SKU123")
                .name("Product Name")
                .imagePath("path/to/image")
                .orderId(10L)
                .orderNumber(100)
                .imageUrl("http://image.url")
                .ready(true)
                .build();

        basicProductDTO = new BasicProductDTO(
                1L,
                "path/to/image",
                "Product Name",
                "SKU123",
                true,
                10L,
                "http://image.url",
                100,
                "1",
                0,
                1

        );
    }

    @Test
    public void testFindAll() {
        // Arrange
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));

        // Act
        var result = productService.findAll();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo(product);
    }

    @Test
    public void testSave() {
        // Arrange
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        Product result = productService.save(basicProductDTO);

        // Assert
        assertThat(result).isEqualTo(product);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testDelete_Success() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        // Act
        boolean result = productService.delete(1L);

        // Assert
        assertThat(result).isTrue();
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    public void testDelete_Failure() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        boolean result = productService.delete(1L);

        // Assert
        assertThat(result).isFalse();
        verify(productRepository, times(0)).delete(any(Product.class));
    }

    @Test
    public void testFindById_Success() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        Product result = productService.findById(1L);

        // Assert
        assertThat(result).isEqualTo(product);
    }

    @Test
    public void testFindById_Failure() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        var exception = org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class, () -> {
            productService.findById(1L);
        });
        assertThat(exception.getMessage()).isEqualTo("The id:1 does not exist");
    }



    @Test
    public void testGetProducts() {
        // Arrange
        when(productRepository.findByOrderNumber(100)).thenReturn(Collections.singletonList(product));

        // Act
        var result = productService.getProducts(100);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo(product);
    }
}
