package com.funky.packageservice.mapper;

import com.funky.packageservice.dto.OrderBasicDTO;
import com.funky.packageservice.dto.ProductBasicDTO;
import com.funky.packageservice.model.Order;
import com.funky.packageservice.model.ProductOrder;

import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderBasicDTO map(Order order) {
        return OrderBasicDTO.builder()
                .id(order.getId())
                .tiendaNubeId(order.getTiendaNubeId())
                .number(order.getNumber())
                .orderStatus(order.getOrderStatus())
                .customer(order.getCustomer())
                .shipStatus(order.getShipStatus())
                .productOrders(order.getProductOrders() != null ? order.getProductOrders().stream().map(OrderMapper::map)
                        .collect(Collectors.toList()) : null)
                .build();
    }

    public static ProductBasicDTO map(ProductOrder productOrder) {
        return ProductBasicDTO.builder()
                .sku(productOrder.getSku())
                .name(productOrder.getName())
                .imagePath(productOrder.getImagePath())
                .orderId(productOrder.getOrderId())
                .build();
    }
}
