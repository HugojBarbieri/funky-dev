package com.funkymonkeys.application.order.dto;


import lombok.Builder;

import java.util.List;

@Builder
public record BasicProductDTO(
        Long id,
        String imagePath,
        String name,
        String sku,
        boolean ready,
        Long orderId,
        String imageUrl,
        int orderNumber,
        String barcode,
        int check,
        int quantity
) {}