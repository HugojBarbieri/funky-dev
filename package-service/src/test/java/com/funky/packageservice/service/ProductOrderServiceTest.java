package com.funky.packageservice.service;

import com.funky.packageservice.model.ProductOrder;
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

public class ProductOrderServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductOrderServiceTest.class);

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductOrderService productOrderService;

    private List<ProductOrder> productOrders;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productOrders = productList();
    }

    @Test
    public void testFindAll() {
        when(productRepository.findAll()).thenReturn(productList());

        List<ProductOrder> productOrders = productOrderService.findAll();

        assertNotNull(productOrders);
        assertEquals(4, productOrders.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        ProductOrder productOrder = productOrders.get(0);

        when(productRepository.save(any(ProductOrder.class))).thenReturn(productOrder);

        ProductOrder savedProductOrder = productOrderService.save(productOrder);

        assertNotNull(savedProductOrder);
        verify(productRepository, times(1)).save(productOrder);
    }

    @Test
    public void testDeleteSuccess() {
        ProductOrder productOrder = productOrders.get(2);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productOrder));

        boolean isDeleted = productOrderService.delete(3L);

        assertTrue(isDeleted);
        verify(productRepository, times(1)).findById(3L);
        verify(productRepository, times(1)).delete(productOrder);
    }

    @Test
    public void testDeleteFailure() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        boolean isDeleted = productOrderService.delete(1L);

        assertFalse(isDeleted);
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(0)).delete(any(ProductOrder.class));
    }

    @Test
    public void testFindByIdSuccess() {
        ProductOrder productOrder = new ProductOrder();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productOrder));

        ProductOrder foundProductOrder = productOrderService.findById(1L);

        assertNotNull(foundProductOrder);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByIdFailure() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            productOrderService.findById(1L);
        });

        assertEquals("The id:1 does not exist", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateToggleSuccess() {
        ProductOrder productOrder = productOrders.get(0);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productOrder));
        when(productRepository.save(any(ProductOrder.class))).thenReturn(productOrder);

        ProductOrder updatedProductOrder = productOrderService.updateToggle(1L);

        assertNotNull(updatedProductOrder);
        assertTrue(updatedProductOrder.isReady());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(productOrder);
    }

    @Test
    public void testUpdateToggleFailure() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            productOrderService.updateToggle(1L);
        });

        assertEquals("The id:1 does not exist", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }

    private List<ProductOrder> productList() {
        return Arrays.asList(
                ProductOrder.builder()
                        .id(1L)
                        .imagePath("path1")
                        .name("CAMPERA NIÑOS FRISADA FUNKY CELESTE (4 años)")
                        .orderId(1L)
                        .ready(false).build(),
                ProductOrder.builder()
                        .id(2L)
                        .name("PANTALON NIÑOS FRISADO FUNKY VERDE (4 años)")
                        .imagePath("path2")
                        .orderId(1L)
                        .ready(false).build(),
                ProductOrder.builder()
                        .id(3L)
                        .name("CAMPERA NIÑOS FRISADA FUNKY CELESTE (3 años)")
                        .imagePath("path3")
                        .orderId(1L)
                        .ready(true).build(),
                ProductOrder.builder()
                        .id(4L)
                        .name("PANTALON NIÑOS FRISADO FUNKY CELESTE (3 años)")
                        .imagePath("path4")
                        .orderId(1L)
                        .ready(true)
                        .build()
        );
    }
}
