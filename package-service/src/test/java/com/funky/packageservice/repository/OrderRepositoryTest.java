package com.funky.packageservice.repository;

import com.funky.packageservice.model.Order;
import com.funky.packageservice.model.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        // Clear the database before each test
        orderRepository.deleteAll();

        // Initialize the database with some test data
        Order order1 = new Order();
        order1.setOrderStatus(OrderStatus.PACKAGED);
        order1.setNumber(1);
        order1.setTiendaNubeId(123L);

        Order order2 = new Order();
        order2.setOrderStatus(OrderStatus.UNPACKAGED);
        order2.setNumber(2);
        order2.setTiendaNubeId(124L);

        orderRepository.save(order1);
        orderRepository.save(order2);
    }

    @Test
    void testFindByOrderStatus_Packaged() {
        List<Order> packagedOrders = orderRepository.findByOrderStatus(OrderStatus.PACKAGED);

        assertThat(packagedOrders.isEmpty()).isFalse();
        assertThat(packagedOrders).hasSize(1);

        Order packagedOrder = packagedOrders.get(0);
        assertThat(packagedOrder.getNumber()).isEqualTo(1);
        assertThat(packagedOrder.getTiendaNubeId()).isEqualTo(123L);
    }

    @Test
    void testFindByOrderStatus_Unpackaged() {
        List<Order> unpackagedOrders = orderRepository.findByOrderStatus(OrderStatus.UNPACKAGED);

        assertThat(unpackagedOrders.isEmpty()).isFalse();
        assertThat(unpackagedOrders).hasSize(1);

        Order unpackagedOrder = unpackagedOrders.get(0);
        assertThat(unpackagedOrder.getNumber()).isEqualTo(2);
        assertThat(unpackagedOrder.getTiendaNubeId()).isEqualTo(124L);
    }

    @Test
    void testFindByOrderStatus_NotFound() {
        List<Order> orders = orderRepository.findByOrderStatus(OrderStatus.CANCELED);

        assertThat(orders.isEmpty()).isTrue();
    }
}
