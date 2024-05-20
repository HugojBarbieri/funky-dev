package com.funky.packageservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.poi.ss.usermodel.Workbook;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ExtendWith(MockitoExtension.class)
public class PackageServiceTest {

    @Mock
    private XLSService xlsService;

    @InjectMocks
    private PackageService packageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PackageServiceTest.class);

    @BeforeEach
    public void setUp() {
        packageService = new PackageService(xlsService);
    }

    @Test
    public void testGetWorkbook() {
        Workbook mockWorkbook = mock(Workbook.class);
        when(xlsService.getWorkbookFromOrders()).thenReturn(mockWorkbook);

        Workbook workbook = packageService.getWorkbook();
        assertNotNull(workbook);
        assertEquals(mockWorkbook, workbook);

        verify(xlsService, times(1)).getWorkbookFromOrders();
    }

    @Test
    public void testFileName() {
        LocalDate fixedDate = LocalDate.of(2024, 5, 20);

        try (MockedStatic<LocalDate> mockedLocalDate = mockStatic(LocalDate.class, CALLS_REAL_METHODS)) {
            mockedLocalDate.when(LocalDate::now).thenReturn(fixedDate);
            String actualFileName = packageService.fileName();
            assertEquals("orders_20_May.xlsx", actualFileName);
        }
    }
}
