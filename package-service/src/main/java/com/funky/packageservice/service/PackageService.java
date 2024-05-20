package com.funky.packageservice.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PackageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PackageService.class);

    private final XLSService xlsService;

    @Autowired
    public PackageService(XLSService xlsService) {
        this.xlsService = xlsService;
    }

    public Workbook getWorkbook() {
        LOGGER.info("Starting to get workbook from service");
        return xlsService.getWorkbookFromOrders();
    }

    public String fileName() {
        LOGGER.info("Starting to getting filename from service");
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MMM");
        String dateFileName = currentDate.format(formatter);
        LOGGER.info(String.format("File name is: %s", dateFileName));
        return String.format("orders_%s.xlsx", dateFileName);
    }
}
