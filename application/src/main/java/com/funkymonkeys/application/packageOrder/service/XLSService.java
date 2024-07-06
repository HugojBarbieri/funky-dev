package com.funkymonkeys.application.packageOrder.service;

import com.funkymonkeys.application.tiendanube.dto.OrderTiendaNubeDTO;
import com.funkymonkeys.application.tiendanube.dto.BasicProductTiendaNubeDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.funkymonkeys.application.packageOrder.util.XLSNamesConstant.*;

@Service
public class XLSService {

    private CellStyle greyCellStyle;
    private CellStyle cellStyle;

    public XLSService() {
    }

    public Workbook getWorkbookFromOrders(List<OrderTiendaNubeDTO> orders) {
        Workbook workbook = new XSSFWorkbook();
        setupStyles(workbook);
        Sheet sheet = setupSheet(workbook);

        writeOrdersToSheet(orders, sheet);

        autoSizeColumns(sheet);

        return workbook;
    }

    private void setupStyles(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);

        cellStyle = createCellStyle(workbook, font);
        greyCellStyle = createGreyCellStyle(workbook, cellStyle);
    }

    private Sheet setupSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet(SHEET_NAME);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE);
        printSetup.setLandscape(false);
        return sheet;
    }

    private void writeOrdersToSheet(List<OrderTiendaNubeDTO> orders, Sheet sheet) {
        int rowIndex = 0;
        int partialIndex = 0;

        for (OrderTiendaNubeDTO order : orders) {
            if (shouldBreakPage(partialIndex, order.products().size())) {
                sheet.setRowBreak(rowIndex);
                partialIndex = 0;
            }

            rowIndex = writeOrder(sheet, order, rowIndex);
            partialIndex += 2;

            rowIndex = writeOrderNotes(sheet, order, rowIndex);
            partialIndex += 2;

            rowIndex = writeProductHeader(sheet, rowIndex);
            rowIndex = writeProducts(sheet, order.products(), rowIndex);

            rowIndex = writeTotalQuantity(sheet, order.products(), rowIndex);
            rowIndex = writeDashRow(sheet, rowIndex);
            rowIndex = writeOwnerNotes(sheet, order, rowIndex);

            partialIndex += rowIndex;
        }
    }

    private boolean shouldBreakPage(int partialIndex, int productCount) {
        return partialIndex > 25 && productCount > 8;
    }

    int writeOrder(Sheet sheet, OrderTiendaNubeDTO order, int rowIndex) {
        Row orderRow = sheet.createRow(rowIndex);
        writeCell(orderRow, 0, order.customer().name(), greyCellStyle);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 1));
        writeCell(orderRow, 2, ORDER_NRO, greyCellStyle);
        writeCell(orderRow, 3, String.valueOf(order.number()), greyCellStyle);
        return rowIndex + 2;
    }

    private int writeOrderNotes(Sheet sheet, OrderTiendaNubeDTO order, int rowIndex) {
        if (order.note() != null && !order.note().isEmpty()) {
            Row noteRow = sheet.createRow(rowIndex);
            writeCell(noteRow, 0, CLIENT_NOTES + ENTER + order.note(), greyCellStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 3));
            return rowIndex + 2;
        }
        return rowIndex;
    }

    int writeProductHeader(Sheet sheet, int rowIndex) {
        Row headerRow = sheet.createRow(rowIndex);
        writeCell(headerRow, 0, "Producto Nombre", cellStyle);
        writeCell(headerRow, 1, "Cantidad", cellStyle);
        return rowIndex + 1;
    }

    int writeProducts(Sheet sheet, List<BasicProductTiendaNubeDTO> productOrderDTOS, int rowIndex) {
        boolean switchFont = true;
        for (BasicProductTiendaNubeDTO productOrderDTO : productOrderDTOS) {
            Row productRow = sheet.createRow(rowIndex++);
            CellStyle currentStyle = switchFont ? greyCellStyle : cellStyle;
            writeCell(productRow, 0, productOrderDTO.name(), currentStyle);
            writeCell(productRow, 1, String.valueOf(productOrderDTO.quantity()), currentStyle);
            switchFont = !switchFont;
        }
        return rowIndex;
    }

    int writeTotalQuantity(Sheet sheet, List<BasicProductTiendaNubeDTO> productOrderDTOS, int rowIndex) {
        Row totalRow = sheet.createRow(rowIndex);
        int totalQuantity = productOrderDTOS.stream().mapToInt(BasicProductTiendaNubeDTO::quantity).sum();
        writeCell(totalRow, 0, "Cantidad Articulos", cellStyle);
        writeCell(totalRow, 1, String.valueOf(totalQuantity), cellStyle);
        return rowIndex + 2;
    }

    private int writeDashRow(Sheet sheet, int rowIndex) {
        Row dashRow = sheet.createRow(rowIndex);
        writeCell(dashRow, 0, "".repeat(60), cellStyle);
        return rowIndex + 1;
    }

    private int writeOwnerNotes(Sheet sheet, OrderTiendaNubeDTO order, int rowIndex) {
        if (order.ownerNote() != null) {
            Row ownerNoteRow = sheet.createRow(rowIndex);
            writeCell(ownerNoteRow, 0, OUR_NOTES + ENTER + order.ownerNote(), greyCellStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 3));
            rowIndex++;
            rowIndex = writeDashRow(sheet, rowIndex);
        }
        return rowIndex;
    }

    private void autoSizeColumns(Sheet sheet) {
        Row firstRow = sheet.getRow(0);
        if (firstRow != null) {
            for (int i = 0; i < firstRow.getLastCellNum(); i++) {
                sheet.autoSizeColumn(i);
            }
        }
    }

    private CellStyle createCellStyle(Workbook workbook, Font font) {
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    private CellStyle createGreyCellStyle(Workbook workbook, CellStyle baseStyle) {
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

    private void writeCell(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }
}
