package com.funky.packageservice.service;

import com.funky.packageservice.client.FunkyClient;
import com.funky.packageservice.dto.OrderDTO;
import com.funky.packageservice.dto.ProductDTO;
import com.funky.packageservice.model.PaymentStatus;
import com.funky.packageservice.model.ProductOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FunkyUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FunkyUtils.class);
    private final FunkyClient funkyClient;

    @Autowired
    public FunkyUtils(FunkyClient funkyClient) {
        this.funkyClient = funkyClient;
    }

    public List<OrderDTO> getUnpackagedAndPaidOrders() {
        return funkyClient.getUnpackagedOrders().stream()
                .filter(orderDTO -> PaymentStatus.PAID.getName()
                        .equals(orderDTO.getPaymentStatus())).collect(Collectors.toList());
    }

    public List<OrderDTO> getUnpackagedOrders() {
        return funkyClient.getUnpackagedOrders();
    }

    public List<ProductDTO> getAllProducts() {
        return funkyClient.getProducts();
    }
}
