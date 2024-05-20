package com.funky.packageservice.service;

import com.funky.packageservice.client.FunkyClient;
import com.funky.packageservice.model.OrderDTO;
import com.funky.packageservice.model.PaymentStatus;
import com.funky.packageservice.model.ProductDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.funky.packageservice.util.XLSNamesConstant.*;

@Service
public class XLSService {

    private CellStyle greyCellStyle;
    private CellStyle cellStyle;


    public Workbook getWorkbookFromOrders(List<OrderDTO> orders) {
        // Create a new Excel workbook
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(SHEET_NAME);

        // Write data to the Excel file
        writeDataToSheet(orders, sheet, workbook);
        return workbook;
    }

    private void writeDataToSheet(List<OrderDTO> orders, Sheet sheet, Workbook workbook) {
        // Create font with larger size
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 8); // Adjust font size as needed

        // Create cell style with the larger font
        cellStyle = getCellStyle(workbook, font);

        // Create a grey cell style with light grey background
        greyCellStyle = getCellStyle(workbook, cellStyle);

        // Set print properties
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE);
        printSetup.setLandscape(false); // Vertical orientation
        int rowIndex = 0;

        for (OrderDTO orderDTO : orders) {
            // Write order details and customer name
            writeOrderDetails(sheet, orderDTO, rowIndex, cellStyle, greyCellStyle);
            rowIndex += 2; // Move to the next row after writing order details and adding an empty row
            writeOrderNotesDetails(sheet, orderDTO, rowIndex, cellStyle, greyCellStyle);
            rowIndex += 2;
            // Write column names for products
            writeProductHeader(sheet, rowIndex, cellStyle);
            rowIndex++;

            // Write product data
            rowIndex = writeProductData(sheet, orderDTO.getProducts(), rowIndex);

            // Add empty row between orders
            rowIndex++;
            totalQuantityProducts(sheet, orderDTO.getProducts().size(), rowIndex, cellStyle);
            rowIndex++;
            rowIndex++;

            // Write a row of dashes between orders
            writeDashRow(sheet, rowIndex, cellStyle);
            rowIndex++;

        }

        // Autosize columns after data is written
        for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private static CellStyle getCellStyle(Workbook workbook, Font font) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        return cellStyle;
    }

    private static CellStyle getCellStyle(Workbook workbook, CellStyle cellStyle) {
        CellStyle greyCellStyle = workbook.createCellStyle();
        greyCellStyle.cloneStyleFrom(cellStyle); // Copy the style from the default cell style
        greyCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        greyCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        return greyCellStyle;
    }

    private void writeOrderNotesDetails(Sheet sheet, OrderDTO orderDTO, int rowIndex, CellStyle cellStyle, CellStyle greyCellStyle) {
        Row orderRow = sheet.createRow(rowIndex);
        writeCell(orderRow, 0, CLIENT_NOTES, greyCellStyle);
        writeCell(orderRow, 1, orderDTO.getNote(), cellStyle);
        writeCell(orderRow, 2, "Notas Nuestras:", greyCellStyle);
        writeCell(orderRow, 3, orderDTO.getOwnerNote(), greyCellStyle);
    }

    private void writeOrderDetails(Sheet sheet, OrderDTO orderDTO, int rowIndex, CellStyle cellStyle, CellStyle greyCellStyle) {
        Row orderRow = sheet.createRow(rowIndex);
        writeCell(orderRow, 0, "Orden NRO:", greyCellStyle);
        writeCell(orderRow, 1, orderDTO.getNumber() + "", greyCellStyle);
        writeCell(orderRow, 2, "Nombre:", greyCellStyle);
        writeCell(orderRow, 3, orderDTO.getCustomer().getName(), greyCellStyle);
        writeCell(orderRow, 4, "PAGADA:", greyCellStyle);
        if (PaymentStatus.PENDING.getName().equals(orderDTO.getPaymentStatus())) {
            writeCell(orderRow, 5, "NO", greyCellStyle);
        } else {
            writeCell(orderRow, 5,  "SI", cellStyle);
        }

        //here is when not paid yet
    }

    private void totalQuantityProducts(Sheet sheet, Integer quantityProducts, int rowIndex, CellStyle cellStyle) {
        Row productHeaderRow = sheet.createRow(rowIndex);
        writeCell(productHeaderRow, 0, "Cantidad Articulos", cellStyle);
        writeCell(productHeaderRow, 1, quantityProducts + "", cellStyle);
    }
    private void writeProductHeader(Sheet sheet, int rowIndex, CellStyle cellStyle) {
        Row productHeaderRow = sheet.createRow(rowIndex);
        writeCell(productHeaderRow, 0, "Producto Nombre", cellStyle);
        writeCell(productHeaderRow, 1, "Cantidad", cellStyle);
    }

    private int writeProductData(Sheet sheet, List<ProductDTO> products, int rowIndex) {
        boolean switchFont = true;
        for (ProductDTO product : products) {
            CellStyle cellStyle = switchFont ? greyCellStyle : this.cellStyle;
            Row productRow = sheet.createRow(rowIndex++);
            writeCell(productRow, 0, product.getName(), cellStyle);
            writeCell(productRow, 1, product.getQuantity() + "", cellStyle);
            switchFont = !switchFont;
        }
        return rowIndex;
    }

    private void writeCell(Row row, int column, String value, CellStyle cellStyle) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }

    private void writeDashRow(Sheet sheet, int rowIndex, CellStyle cellStyle) {
        Row dashRow = sheet.createRow(rowIndex);
        writeCell(dashRow, 0, "#".repeat(35), cellStyle);
    }
}
