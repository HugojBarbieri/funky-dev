package com.funky.packageservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.funky.packageservice.dto.OrderDTO;
import com.funky.packageservice.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PackageServiceTest {

    @Mock
    private FunkyUtils funkyUtils;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private PackageService packageService;

    private Order packagedOrder1;
    private Order packagedOrder2;
    private OrderDTO unpackagedOrder1;
    private OrderDTO unpackagedOrder2;

    @BeforeEach
    void setUp() {
        packagedOrder1 = new Order();
        packagedOrder1.setNumber(1);
        packagedOrder1.setTiendaNubeId(101L);

        packagedOrder2 = new Order();
        packagedOrder2.setNumber(2);
        packagedOrder2.setTiendaNubeId(102L);

        unpackagedOrder1 = new OrderDTO();
        unpackagedOrder1.setNumber(1);
        unpackagedOrder1.setId(101L);

        unpackagedOrder2 = new OrderDTO();
        unpackagedOrder2.setNumber(3);
        unpackagedOrder2.setId(103L);
    }

    @Test
    void testGetUnpackagedOrders_MatchingOrders() {
        when(orderService.findByPackaged()).thenReturn(Arrays.asList(packagedOrder1, packagedOrder2));
        when(funkyUtils.getUnpackagedAndPaidOrders()).thenReturn(Arrays.asList(unpackagedOrder1, unpackagedOrder2));

        Optional<List<OrderDTO>> result = packageService.getUnpackagedOrders();

        assertThat(result).isPresent();
        assertThat(result.get().size()).isEqualTo(1);
        verify(orderService, times(1)).findByPackaged();
        verify(funkyUtils, times(1)).getUnpackagedAndPaidOrders();
    }

    @Test
    void testGetUnpackagedOrders_NoMatchingOrders() {
        when(orderService.findByPackaged()).thenReturn(Arrays.asList(packagedOrder1, packagedOrder2));
        when(funkyUtils.getUnpackagedAndPaidOrders()).thenReturn(Collections.singletonList(unpackagedOrder2));

        Optional<List<OrderDTO>> result = packageService.getUnpackagedOrders();

        assertThat(result).isPresent();
        assertThat(result.get().size()).isEqualTo(1);
        verify(orderService, times(1)).findByPackaged();
        verify(funkyUtils, times(1)).getUnpackagedAndPaidOrders();
    }

    @Test
    void testGetUnpackagedOrders_EmptyPackagedOrders() {
        when(orderService.findByPackaged()).thenReturn(Collections.emptyList());
        when(funkyUtils.getUnpackagedAndPaidOrders()).thenReturn(Arrays.asList(unpackagedOrder1, unpackagedOrder2));

        Optional<List<OrderDTO>> result = packageService.getUnpackagedOrders();

        assertThat(result).isPresent();
        assertThat(result.get().size()).isEqualTo(2);
        verify(orderService, times(1)).findByPackaged();
        verify(funkyUtils, times(1)).getUnpackagedAndPaidOrders();
    }

    @Test
    void testGetUnpackagedOrders_EmptyUnpackagedOrders() {
        when(orderService.findByPackaged()).thenReturn(Arrays.asList(packagedOrder1, packagedOrder2));
        when(funkyUtils.getUnpackagedAndPaidOrders()).thenReturn(Collections.emptyList());

        Optional<List<OrderDTO>> result = packageService.getUnpackagedOrders();

        assertThat(result).isPresent();
        assertThat(result.get()).isEmpty();
        verify(orderService, times(1)).findByPackaged();
        verify(funkyUtils, times(1)).getUnpackagedAndPaidOrders();
    }
}
