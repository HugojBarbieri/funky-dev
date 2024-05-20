package com.funky.packageservice.controller;

import com.funky.packageservice.PackageService;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;


@RestController
@RequestMapping("/package")
public class PackageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PackageController.class);

    private final PackageService packageService;

    @Autowired
    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @GetMapping("/download-excel")
    public ResponseEntity<byte[]> downloadExcel() {
        try {
            Workbook workbook = packageService.getWorkbook();

            // Convert the workbook to a byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] excelBytes = outputStream.toByteArray();

            // Set headers for the response
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=orders.xlsx");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            LOGGER.error("Failed to create the Excel file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/save-excel")
    public ResponseEntity<String> saveExcel() {
        try {
            Workbook workbook = packageService.getWorkbook();
            // Save the workbook to a file
            FileOutputStream fileOut = new FileOutputStream("orders.xlsx");
            workbook.write(fileOut);
            fileOut.close();

            return ResponseEntity.ok("Excel file saved successfully in path");
        } catch (IOException e) {
            LOGGER.error("Failed to send create the excel", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save Excel file.");
        }
    }
}
