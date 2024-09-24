package com.funkymonkeys.application.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.funkymonkeys.application.order.dto.OrderDTO;
import com.funkymonkeys.application.order.model.Order;
import com.funkymonkeys.application.order.model.OrderStatus;
import com.funkymonkeys.application.order.model.ShipStatus;
import com.funkymonkeys.application.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private OrderDTO orderDTO;

    @BeforeEach
    void setUp() {
        order = Order.builder()
                .id(1L)
                .tiendaNubeId(123L)
                .number(1)
                .orderStatus(OrderStatus.UNPACKAGED)
                .shipStatus(ShipStatus.CORREO_ARGENTINO)
                .customer("John Doe")
                .build();

        orderDTO = new OrderDTO(
                123L,
                1L,
                437,
                OrderStatus.UNPACKAGED,
                ShipStatus.CORREO_ARGENTINO,
                "John Doe",
                Collections.emptyList()
        );
    }

    @Test
    void testFindAll() {
        // Arrange
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(order));

        // Act
        List<Order> result = orderService.findAll();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo(order);
    }

    @Test
    void testSave() {
        // Arrange
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Act
        Order result = orderService.save(orderDTO);

        // Assert
        assertThat(result).isEqualTo(order);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void testDelete() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // Act
        boolean result = orderService.delete(1L);

        // Assert
        assertThat(result).isTrue();
        verify(orderRepository).delete(order);
    }

    @Test
    void testDeleteNotFound() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        boolean result = orderService.delete(1L);

        // Assert
        assertThat(result).isFalse();
        verify(orderRepository, never()).delete(any(Order.class));
    }

    @Test
    void testFindById() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // Act
        Optional<Order> result = orderService.findById(1L);

        // Assert
        assertThat(result).isPresent().contains(order);
    }

    @Test
    void testFindByPackaged() {
        // Arrange
        when(orderRepository.findByOrderStatus(OrderStatus.PACKAGED)).thenReturn(Collections.singletonList(order));

        // Act
        List<Order> result = orderService.findByPackaged();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo(order);
    }

    @Test
    void testPackaged() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Act
        Order result = orderService.packaged(1L);

        // Assert
        assertThat(result.getOrderStatus()).isEqualTo(OrderStatus.PACKAGED);
        verify(orderRepository).save(order);
    }

    @Test
    void testPackagedNotFound() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> orderService.packaged(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("The id:1 does not exist");
    }


}
