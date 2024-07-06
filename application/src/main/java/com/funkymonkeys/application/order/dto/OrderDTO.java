package com.funkymonkeys.application.order.dto;

import com.funkymonkeys.application.order.model.OrderStatus;
import com.funkymonkeys.application.order.model.ShipStatus;
import lombok.*;

import java.util.List;

@Builder
public record OrderDTO(Long id, Long tiendaNubeId, int number, OrderStatus orderStatus,
                       ShipStatus shipStatus, String customer, List<ProductDTO> products)
{
}
