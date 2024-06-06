package com.funky.packageservice.service;

import com.funky.packageservice.dto.CustomerOrderDTO;
import com.funky.packageservice.dto.OrderDTO;
import com.funky.packageservice.dto.ProductOrderDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class XLSServiceTest {

    private XLSService xlsService;

    @BeforeEach
    public void setUp() {
        xlsService = new XLSService();
    }

    @Test
    public void testGetWorkbookFromOrders() {
        // Given
        OrderDTO order = createMockOrder();
        List<OrderDTO> orders = Arrays.asList(order);

        // When
        Workbook workbook = xlsService.getWorkbookFromOrders(orders);

        // Then
        assertNotNull(workbook);
        assertEquals(1, workbook.getNumberOfSheets());
        Sheet sheet = workbook.getSheetAt(0);
        assertNotNull(sheet);
        assertEquals("Orders", sheet.getSheetName()); // Update the expected sheet name if needed
        // Add more assertions as necessary to verify the content of the sheet
    }

    private OrderDTO createMockOrder() {
        OrderDTO order = mock(OrderDTO.class);
        CustomerOrderDTO customer = mock(CustomerOrderDTO.class);
        ProductOrderDTO product1 = mock(ProductOrderDTO.class);
        ProductOrderDTO product2 = mock(ProductOrderDTO.class);

        when(customer.getName()).thenReturn("Customer Name");
        when(order.getCustomer()).thenReturn(customer);

        when(product1.getName()).thenReturn("Product 1");
        when(product1.getQuantity()).thenReturn(10);
        when(product2.getName()).thenReturn("Product 2");
        when(product2.getQuantity()).thenReturn(20);

        List<ProductOrderDTO> products = Arrays.asList(product1, product2);

        when(order.getNumber()).thenReturn(12345);
        when(order.getNote()).thenReturn("Order Note");
        when(order.getProducts()).thenReturn(products);
        when(order.getOwnerNote()).thenReturn("Owner Note");

        return order;
    }

    @Test
    public void testWriteOrder() {
        // Given
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        OrderDTO order = createMockOrder();

        // When
        int rowIndex = xlsService.writeOrder(sheet, order, 0);

        // Then
        assertEquals(2, rowIndex);
        Row row = sheet.getRow(0);
        assertNotNull(row);
        Cell cell = row.getCell(0);
        assertNotNull(cell);
        assertEquals("Customer Name", cell.getStringCellValue());
        // Add more assertions as necessary
    }

    @Test
    public void testWriteProductHeader() {
        // Given
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        // When
        int rowIndex = xlsService.writeProductHeader(sheet, 0);

        // Then
        assertEquals(1, rowIndex);
        Row row = sheet.getRow(0);
        assertNotNull(row);
        assertEquals("Producto Nombre", row.getCell(0).getStringCellValue());
        assertEquals("Cantidad", row.getCell(1).getStringCellValue());
    }

    @Test
    public void testWriteProducts() {
        // Given
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        ProductOrderDTO product1 = mock(ProductOrderDTO.class);
        ProductOrderDTO product2 = mock(ProductOrderDTO.class);

        when(product1.getName()).thenReturn("Product 1");
        when(product1.getQuantity()).thenReturn(10);
        when(product2.getName()).thenReturn("Product 2");
        when(product2.getQuantity()).thenReturn(20);

        List<ProductOrderDTO> products = Arrays.asList(product1, product2);

        // When
        int rowIndex = xlsService.writeProducts(sheet, products, 0);

        // Then
        assertEquals(2, rowIndex);
        Row row1 = sheet.getRow(0);
        assertNotNull(row1);
        assertEquals("Product 1", row1.getCell(0).getStringCellValue());
        assertEquals("10", row1.getCell(1).getStringCellValue());

        Row row2 = sheet.getRow(1);
        assertNotNull(row2);
        assertEquals("Product 2", row2.getCell(0).getStringCellValue());
        assertEquals("20", row2.getCell(1).getStringCellValue());
    }

    @Test
    public void testWriteTotalQuantity() {
        // Given
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        ProductOrderDTO product1 = mock(ProductOrderDTO.class);
        ProductOrderDTO product2 = mock(ProductOrderDTO.class);

        when(product1.getQuantity()).thenReturn(10);
        when(product2.getQuantity()).thenReturn(20);

        List<ProductOrderDTO> products = Arrays.asList(product1, product2);

        // When
        int rowIndex = xlsService.writeTotalQuantity(sheet, products, 0);

        // Then
        assertEquals(2, rowIndex);
        Row row = sheet.getRow(0);
        assertNotNull(row);
        assertEquals("Cantidad Articulos", row.getCell(0).getStringCellValue());
        assertEquals("30", row.getCell(1).getStringCellValue());
    }

    // Add more tests for other methods as necessary
}
