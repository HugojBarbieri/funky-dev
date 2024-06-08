package com.funky.packageservice.service;

import com.funky.packageservice.client.FunkyClient;
import com.funky.packageservice.dto.OrderDTO;
import com.funky.packageservice.model.Order;
import com.funky.packageservice.model.PaymentStatus;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PackageServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(PackageServiceImpl.class);

    private final XLSService xlsService;
    private final FunkyClient funkyClient;
    private final OrderService orderService;

    @Autowired
    public PackageServiceImpl(XLSService xlsService, FunkyClient funkyClient, OrderService orderService) {
        this.xlsService = xlsService;
        this.funkyClient = funkyClient;
        this.orderService = orderService;
    }

    public List<OrderDTO> getUnpackagedAndPaidOrders() {
        return funkyClient.getUnpackagedOrders().stream()
                .filter(orderDTO -> PaymentStatus.PAID.getName()
                        .equals(orderDTO.getPaymentStatus())).collect(Collectors.toList());
    }

    public Optional<List<Order>> importOrders() {
        List<OrderDTO> orderDTOS = getUnpackagedAndPaidOrders();
        //TODO find all orders unpackaged and saved it,
        //
        return null;

    }

    public List<OrderDTO> getUnpackagedOrders() {
        return funkyClient.getUnpackagedOrders();
    }

    public Workbook getWorkbook() {
        LOGGER.info("Starting to get workbook from service");
        return xlsService.getWorkbookFromOrders(getUnpackagedAndPaidOrders());
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
