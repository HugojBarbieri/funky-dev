package com.funkymonkeys.application.order.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.funkymonkeys.application.order.model.Order;
import com.funkymonkeys.application.order.model.OrderStatus;
import com.funkymonkeys.application.order.model.ShipStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@DataJpaTest
@SpringJUnitConfig
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        // Set up test data
        Order order1 = Order.builder()
                .tiendaNubeId(1L)
                .number(101)
                .orderStatus(OrderStatus.PACKAGED)
                .shipStatus(ShipStatus.CORREO_ARGENTINO)
                .customer("Customer 1")
                .build();

        Order order2 = Order.builder()
                .tiendaNubeId(2L)
                .number(102)
                .orderStatus(OrderStatus.UNPACKAGED)
                .shipStatus(ShipStatus.ANDREANI)
                .customer("Customer 2")
                .build();

        Order order3 = Order.builder()
                .tiendaNubeId(3L)
                .number(103)
                .orderStatus(OrderStatus.PACKAGED)
                .shipStatus(ShipStatus.OCA)
                .customer("Customer 3")
                .build();

        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
    }

    @Test
    @Disabled("This test is ignored for now")
    public void testFindByOrderStatus() {
        List<Order> packagedOrders = orderRepository.findByOrderStatus(OrderStatus.PACKAGED);
        assertThat(packagedOrders).hasSize(2);
        assertThat(packagedOrders).extracting(Order::getCustomer)
                .containsExactlyInAnyOrder("Customer 1", "Customer 3");

        List<Order> unpackagedOrders = orderRepository.findByOrderStatus(OrderStatus.UNPACKAGED);
        assertThat(unpackagedOrders).hasSize(1);
        assertThat(unpackagedOrders).extracting(Order::getCustomer)
                .containsExactly("Customer 2");
    }
}
