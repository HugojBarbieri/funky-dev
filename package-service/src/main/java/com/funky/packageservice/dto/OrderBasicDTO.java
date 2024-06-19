package com.funky.packageservice.dto;

import com.funky.packageservice.model.OrderStatus;
import com.funky.packageservice.model.ProductOrder;
import com.funky.packageservice.model.ShipStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderBasicDTO {

    private Long id;
    private Long tiendaNubeId;
    private int number;
    private OrderStatus orderStatus;
    private ShipStatus shipStatus;
    private String customer;
    private List<ProductBasicDTO> productOrders;
}
