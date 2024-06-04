package com.funky.packageservice.service;

import com.funky.packageservice.model.Order;
import com.funky.packageservice.model.Product;
import com.funky.packageservice.model.ShipStatus;
import com.funky.packageservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        when(orderRepository.findAll()).thenReturn(createAMockList());

        List<Order> orders = orderService.findAll();

        assertNotNull(orders);
        assertEquals(2, orders.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        Order order = createAMockList().get(0);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order savedOrder = orderService.save(order);

        assertNotNull(savedOrder);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testDeleteSuccess() {
        Order order = createAMockList().get(0);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        boolean isDeleted = orderService.delete(1L);

        assertTrue(isDeleted);
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).delete(order);
    }

    @Test
    public void testDeleteFailure() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        boolean isDeleted = orderService.delete(1L);

        assertFalse(isDeleted);
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(0)).delete(any(Order.class));
    }

    @Test
    public void testFindByIdSuccess() {
        Order order = createAMockList().get(1);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        Order foundOrder = orderService.findById(2L);

        assertNotNull(foundOrder);
        verify(orderRepository, times(1)).findById(2L);
    }

    @Test
    public void testFindByIdFailure() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            orderService.findById(1L);
        });

        assertEquals("The id:1 does not exist", exception.getMessage());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateSuccess() {
        Order order = createAMockList().get(0);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order updatedOrder = orderService.update(1L, true);

        assertNotNull(updatedOrder);
        assertTrue(updatedOrder.isPackaged());
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testUpdateFailure() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            orderService.update(1L, true);
        });

        assertEquals("The id:1 does not exist", exception.getMessage());
        verify(orderRepository, times(1)).findById(1L);
    }

    private List<Order> createAMockList() {
        return Arrays.asList(Order.builder()
                        .products(Collections.singletonList(Product.builder()
                                .id(1L)
                                .imagePath("path1")
                                .name("CAMPERA NIÑOS FRISADA FUNKY CELESTE (4 años)")
                                .orderId(1L)
                                .ready(false).build()))
                        .id(1L)
                        .number(42)
                        .customer("venta")
                        .packaged(false)
                        .shipStatus(ShipStatus.ANDREANI)
                        .tiendaNubeId(123L)
                        .build(),
                Order.builder()
                        .products(Collections.singletonList(Product.builder()
                                .id(2L)
                                .imagePath("path2")
                                .name("PANTALON NIÑOS FRISADO FUNKY VERDE (4 años)")
                                .orderId(2L)
                                .ready(false).build()))
                        .id(2L)
                        .number(84)
                        .customer("venta2")
                        .packaged(false)
                        .shipStatus(ShipStatus.CORREO_ARGENTINO)
                        .tiendaNubeId(124L)
                        .build());
    }
}
