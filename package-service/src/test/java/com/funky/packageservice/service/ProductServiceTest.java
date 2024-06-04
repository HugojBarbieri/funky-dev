package com.funky.packageservice.service;

import com.funky.packageservice.model.Product;
import com.funky.packageservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceTest.class);

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private List<Product> products;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        products = productList();
    }

    @Test
    public void testFindAll() {
        when(productRepository.findAll()).thenReturn(productList());

        List<Product> products = productService.findAll();

        assertNotNull(products);
        assertEquals(4, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        Product product = products.get(0);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.save(product);

        assertNotNull(savedProduct);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testDeleteSuccess() {
        Product product = products.get(2);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        boolean isDeleted = productService.delete(3L);

        assertTrue(isDeleted);
        verify(productRepository, times(1)).findById(3L);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    public void testDeleteFailure() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        boolean isDeleted = productService.delete(1L);

        assertFalse(isDeleted);
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(0)).delete(any(Product.class));
    }

    @Test
    public void testFindByIdSuccess() {
        Product product = new Product();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        Product foundProduct = productService.findById(1L);

        assertNotNull(foundProduct);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByIdFailure() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            productService.findById(1L);
        });

        assertEquals("The id:1 does not exist", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateToggleSuccess() {
        Product product = products.get(0);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product updatedProduct = productService.updateToggle(1L);

        assertNotNull(updatedProduct);
        assertTrue(updatedProduct.isReady());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testUpdateToggleFailure() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            productService.updateToggle(1L);
        });

        assertEquals("The id:1 does not exist", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
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
