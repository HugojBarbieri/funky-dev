package com.funky.packageservice.service;

import com.funky.packageservice.client.FunkyClient;
import com.funky.packageservice.dto.OrderDTO;
import com.funky.packageservice.model.PaymentStatus;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PackageServiceXLSImplTest {

//    @Mock
//    private XLSService xlsService;
//
//    @Mock
//    private FunkyClient funkyClient;
//
//    @InjectMocks
//    private PackageServiceXLSImpl packageServiceXLSImpl;
//
//    @InjectMocks
//    private OrderService orderService;
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(PackageServiceXLSImplTest.class);
//
//    @BeforeEach
//    public void setUp() {
//        packageServiceXLSImpl = new PackageServiceXLSImpl(xlsService, funkyClient, orderService);
//    }
//
//    @Test
//    public void testGetWorkbook() {
//        Workbook mockWorkbook = mock(Workbook.class);
//        List<OrderDTO> mockOrders = new ArrayList<>();
//        when(funkyClient.getUnpackagedOrders()).thenReturn(mockOrders);
//        when(xlsService.getWorkbookFromOrders(mockOrders)).thenReturn(mockWorkbook);
//
//        Workbook workbook = packageServiceXLSImpl.getWorkbook();
//        assertNotNull(workbook);
//        assertEquals(mockWorkbook, workbook);
//
//        verify(xlsService, times(1)).getWorkbookFromOrders(anyList());
//    }
//
//    @Test
//    public void testFileName() {
//        LocalDate fixedDate = LocalDate.of(2024, 5, 20);
//
//        try (MockedStatic<LocalDate> mockedLocalDate = mockStatic(LocalDate.class, CALLS_REAL_METHODS)) {
//            mockedLocalDate.when(LocalDate::now).thenReturn(fixedDate);
//            String actualFileName = packageServiceXLSImpl.fileName();
//            assertEquals("orders_20_May.xlsx", actualFileName);
//        }
//    }
//
//    @Test
//    public void testGetUnpackagedAndPaidOrders() {
//        // Given
//        OrderDTO order1 = new OrderDTO();
//        order1.setPaymentStatus(PaymentStatus.PAID.getName());
//
//        OrderDTO order2 = new OrderDTO();
//        order2.setPaymentStatus(PaymentStatus.PENDING.getName());
//
//        OrderDTO order3 = new OrderDTO();
//        order3.setPaymentStatus(PaymentStatus.PAID.getName());
//
//        List<OrderDTO> unpackagedOrders = Arrays.asList(order1, order2, order3);
//
//        when(funkyClient.getUnpackagedOrders()).thenReturn(unpackagedOrders);
//
//        // When
//        List<OrderDTO> result = packageServiceXLSImpl.getUnpackagedAndPaidOrders();
//
//        // Then
//        List<OrderDTO> expected = unpackagedOrders.stream()
//                .filter(orderDTO -> PaymentStatus.PAID.getName().equals(orderDTO.getPaymentStatus()))
//                .collect(Collectors.toList());
//
//        assertEquals(expected, result);
//    }
}
