package com.funky.packageservice.service;

import com.funky.packageservice.model.Order;
import com.funky.packageservice.model.OrderStatus;
import com.funky.packageservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order1;
    private Order order2;

    @BeforeEach
    void setUp() {
        order1 = new Order();
        order1.setId(1L);
        order1.setOrderStatus(OrderStatus.PACKAGED);

        order2 = new Order();
        order2.setId(2L);
        order2.setOrderStatus(OrderStatus.UNPACKAGED);
    }

    @Test
    void testFindAll() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = orderService.findAll();

        assertThat(orders).hasSize(2);
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testSave() {
        when(orderRepository.save(any(Order.class))).thenReturn(order1);

        Order savedOrder = orderService.save(order1);

        assertThat(savedOrder).isEqualTo(order1);
        verify(orderRepository, times(1)).save(order1);
    }

    @Test
    void testDeleteExistingOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order1));

        boolean isDeleted = orderService.delete(1L);

        assertThat(isDeleted).isTrue();
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).delete(order1);
    }

    @Test
    void testDeleteNonExistingOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        boolean isDeleted = orderService.delete(1L);

        assertThat(isDeleted).isFalse();
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(0)).delete(any(Order.class));
    }

    @Test
    void testFindById() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order1));

        Optional<Order> foundOrder = orderService.findById(1L);

        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get()).isEqualTo(order1);
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByPackaged() {
        when(orderRepository.findByOrderStatus(OrderStatus.PACKAGED)).thenReturn(Arrays.asList(order1));

        List<Order> packagedOrders = orderService.findByPackaged();

        assertThat(packagedOrders).hasSize(1);
        assertThat(packagedOrders.get(0)).isEqualTo(order1);
        verify(orderRepository, times(1)).findByOrderStatus(OrderStatus.PACKAGED);
    }

    @Test
    void testPackagedExistingOrder() {
        when(orderRepository.findById(2L)).thenReturn(Optional.of(order2));
        when(orderRepository.save(any(Order.class))).thenReturn(order2);

        Order packagedOrder = orderService.packaged(2L);

        assertThat(packagedOrder.getOrderStatus()).isEqualTo(OrderStatus.PACKAGED);
        verify(orderRepository, times(1)).findById(2L);
        verify(orderRepository, times(1)).save(order2);
    }

    @Test
    void testPackagedNonExistingOrder() {
        when(orderRepository.findById(3L)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            orderService.packaged(3L);
        });

        assertThat(exception.getMessage()).isEqualTo("The id:3 does not exist");
        verify(orderRepository, times(1)).findById(3L);
        verify(orderRepository, times(0)).save(any(Order.class));
    }
}
