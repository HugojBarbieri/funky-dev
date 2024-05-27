package com.funky.packageservice.service;

import com.funky.packageservice.model.OrderDTO;
import com.funky.packageservice.model.ProductDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.util.CellRangeAddress;


import java.util.List;

import static com.funky.packageservice.util.XLSNamesConstant.*;

@Service
public class XLSService {

    private CellStyle greyCellStyle;
    private CellStyle cellStyle;
    private static int rowIndex = 0;
    private static int partial_index = 0;
    private static boolean breakPage = false;

    public XLSService() {
    }

    public Workbook getWorkbookFromOrders(List<OrderDTO> orders) {
        // Create a new Excel workbook
        Workbook workbook = new XSSFWorkbook();
        Font font = workbook.createFont();
        short height = (short) 10;
        font.setFontHeightInPoints(height);

        cellStyle = getCellStyle(workbook, font);
        greyCellStyle = getGreyCellStyle(workbook);

        Sheet sheet = workbook.createSheet(SHEET_NAME);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE);
        printSetup.setLandscape(false);

        // Write data to the Excel file
        writeDataToSheet(orders, sheet);
        return workbook;
    }

    private void writeDataToSheet(List<OrderDTO> orders, Sheet sheet) {
        for (OrderDTO orderDTO : orders) {
            if(breakPage || (partial_index > 25) && orderDTO.getProducts().size() > 8){
                sheet.setRowBreak(rowIndex);
                breakPage = false;
                partial_index = 0;
            }
            // Write order details and customer name
            writeOrderDetails(sheet, orderDTO);
            checkAndIncrement(2); // Move to the next row after writing order details and adding an empty row
            if(orderDTO.getNote() != null && !"".equals(orderDTO.getNote())) {
                writeOrderNotesDetails(sheet, orderDTO);
                checkAndIncrement(2);
            }

            // Write column names for products
            writeProductHeader(sheet);
            checkAndIncrement(1);

            // Write product data
            writeProductData(sheet, orderDTO.getProducts());

            // Add empty row between orders
            checkAndIncrement(2);
            totalQuantityProducts(sheet, orderDTO.getProducts().stream().mapToInt(ProductDTO::getQuantity).sum());
            checkAndIncrement(2);

            // Write a row of dashes between orders
            writeDashRow(sheet);
            checkAndIncrement(1);
            if(orderDTO.getOwnerNote() != null) {
                writeOurNotes(sheet, orderDTO);
                checkAndIncrement(1);
                writeDashRow(sheet);
            }
        }

        // Autosize columns after data is written
        Row firstRow = sheet.getRow(0);
        if (firstRow != null) {
            for (int i = 0; i < firstRow.getLastCellNum(); i++) {
                sheet.autoSizeColumn(i);
            }
        }
        rowIndex = 0;
    }

    private void checkAndIncrement(int inc) {
        rowIndex = rowIndex + inc;
        partial_index = partial_index + inc;
        breakPage = partial_index > 35;
    }

    private CellStyle getCellStyle(Workbook workbook, Font font) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        return cellStyle;
    }

    private CellStyle getGreyCellStyle(Workbook workbook) {
        CellStyle borderedCellStyle = workbook.createCellStyle();
        borderedCellStyle.cloneStyleFrom(this.cellStyle); // Copy the style from the default cell style

        // Set the border style
        borderedCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        borderedCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        borderedCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        borderedCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);

        // Set the border color (optional, you can choose the desired color)
        short borderColor = IndexedColors.BLACK.getIndex();
        borderedCellStyle.setTopBorderColor(borderColor);
        borderedCellStyle.setBottomBorderColor(borderColor);
        borderedCellStyle.setLeftBorderColor(borderColor);
        borderedCellStyle.setRightBorderColor(borderColor);
        return borderedCellStyle;

    }

    private void writeOrderNotesDetails(Sheet sheet, OrderDTO orderDTO) {
        Row orderRow = sheet.createRow(rowIndex);
        writeCell(orderRow, 0, CLIENT_NOTES + ENTER + orderDTO.getNote() , this.greyCellStyle);
        sheet.addMergedRegion(new CellRangeAddress(orderRow.getRowNum(), orderRow.getRowNum(), 0, 3));
    }

    private void writeOrderDetails(Sheet sheet, OrderDTO orderDTO) {
        Row orderRow = sheet.createRow(rowIndex);
        writeCell(orderRow, 0, orderDTO.getCustomer().getName(), this.greyCellStyle);
        sheet.addMergedRegion(new CellRangeAddress(orderRow.getRowNum(), orderRow.getRowNum(), 0, 1));
        //writeCell(orderRow, 1, EMPTY, greyCellStyle);
        writeCell(orderRow, 2, ORDER_NRO, this.greyCellStyle);
        writeCell(orderRow, 3, orderDTO.getNumber() + "", this.greyCellStyle);
    }


    private void totalQuantityProducts(Sheet sheet, Integer quantityProducts) {
        Row productHeaderRow = sheet.createRow(rowIndex);
        writeCell(productHeaderRow, 0, "Cantidad Articulos", this.cellStyle);
        writeCell(productHeaderRow, 1, quantityProducts + "", this.cellStyle);
    }
    private void writeProductHeader(Sheet sheet) {
        Row productHeaderRow = sheet.createRow(rowIndex);
        writeCell(productHeaderRow, 0, "Producto Nombre", this.cellStyle);
        writeCell(productHeaderRow, 1, "Cantidad", this.cellStyle);
    }

    private void writeProductData(Sheet sheet, List<ProductDTO> products) {
        boolean switchFont = true;
        for (ProductDTO product : products) {
            CellStyle cellStyle = switchFont ? this.greyCellStyle : this.cellStyle;
            checkAndIncrement(1);
            Row productRow = sheet.createRow(rowIndex);
            writeCell(productRow, 0, product.getName(), cellStyle);
            writeCell(productRow, 1, product.getQuantity() + "", cellStyle);
            switchFont = !switchFont;
        }
    }

    private void writeCell(Row row, int column, String value, CellStyle cellStyle) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }

    private void writeOurNotes(Sheet sheet, OrderDTO orderDTO) {
        Row orderRow = sheet.createRow(rowIndex);
        writeCell(orderRow, 0, OUR_NOTES + ENTER + orderDTO.getOwnerNote(), this.greyCellStyle);
        sheet.addMergedRegion(new CellRangeAddress(orderRow.getRowNum(), orderRow.getRowNum(), 0, 3));
   }

    private void writeDashRow(Sheet sheet) {
        Row orderRow = sheet.createRow(rowIndex);
        writeCell(orderRow, 0, "#".repeat(60), this.cellStyle);
    }
}
