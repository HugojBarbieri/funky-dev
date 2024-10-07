package com.funkymonkeys.application.packageOrder.service;

import com.funkymonkeys.application.order.dto.BasicProductDTO;
import com.funkymonkeys.application.order.dto.OrderDTO;
import com.funkymonkeys.application.order.model.Order;
import com.funkymonkeys.application.order.service.OrderService;
import com.funkymonkeys.application.order.service.ProductService;
import com.funkymonkeys.application.tiendanube.dto.BasicProductTiendaNubeDTO;
import com.funkymonkeys.application.tiendanube.dto.OrderTiendaNubeDTO;
import com.funkymonkeys.application.tiendanube.service.TiendaNubeService;
import com.funkymonkeys.application.util.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackageService {

    private final TiendaNubeService tiendaNubeService;
    private final OrderService orderService;
    private final ProductService productService;
    private final EmailService emailService;

    @Autowired
    public PackageService(TiendaNubeService tiendaNubeService, OrderService orderService, ProductService productService, EmailService emailService) {
        this.tiendaNubeService = tiendaNubeService;
        this.orderService = orderService;
        this.productService = productService;
        this.emailService = emailService;
    }

    public Optional<List<OrderTiendaNubeDTO>> getUnpackagedOrders() {
        // Step 1: Fetch the orders from the TiendaNube service
        List<OrderTiendaNubeDTO> ordersFromFunky = tiendaNubeService.getUnpackagedOrders();

        // Step 2: Mirror these orders with your local database (assuming this method returns a list of Order objects)
        dBMirror(ordersFromFunky);

        // Step 4: Return the list wrapped in an Optional
        return Optional.of(ordersFromFunky);
    }


    private List<Order> dBMirror(List<OrderTiendaNubeDTO> ordersFromFunky) {
        ordersFromFunky.forEach(o -> {
            Order order = orderService.save(OrderDTO.builder()
                    .tiendaNubeId(o.getId())
                    .customer(o.getCustomer().name())
                    .number(o.getNumber())
                    .build());
            o.setOrderStatus(order.getOrderStatus().name());
            o.getProducts().forEach(p -> {
                productService.save(BasicProductDTO.builder()
                        .sku(p.getSku())
                        .ready(false)
                        .id(p.getId())
                        .orderId(order.getId())
                        .orderNumber(order.getNumber())
                        .imageUrl(p.getImage().src())
                        .name(p.getName())
                        .barcode(p.getBarcode())
                        .quantity(p.getQuantity())
                        .build());
            });
        });


        return orderService.findByUnPackaged();
    }

    public Optional<List<BasicProductTiendaNubeDTO>> getUnpackagedProducts(int orderNumber) {
        // Fetch all unpackaged orders from TiendaNubeService
        List<OrderTiendaNubeDTO> unpackagedOrders = tiendaNubeService.getUnpackagedOrders();

        // Retrieve the product IDs for the given order number
        List<Long> productIds = productService.getReadyProducts(orderNumber);

        // Find the specific order by order number
        OrderTiendaNubeDTO matchingOrder = unpackagedOrders.stream()
                .filter(o -> o.getNumber() == orderNumber).toList().getFirst();


        matchingOrder.getProducts().forEach(p -> {
            if(productIds.contains(p.getId())) {
                p.setReady(true);
            }
        });

        // If there are products associated with the order, find the first unpackaged product
        return Optional.of(matchingOrder.getProducts());
    }

    public OrderDTO packaged(Long id) {
        Order packaged = orderService.packaged(id);
        emailService.sendEmail(packaged);
        return OrderDTO.builder()
                .orderStatus(packaged.getOrderStatus())
                .id(packaged.getId())
                .shipStatus(packaged.getShipStatus())
                .number(packaged.getNumber())
                .build();
    }
}
