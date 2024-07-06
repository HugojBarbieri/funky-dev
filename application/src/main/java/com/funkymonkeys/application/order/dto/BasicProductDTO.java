package com.funkymonkeys.application.order.dto;


import lombok.Builder;

import java.util.List;

@Builder
public record BasicProductDTO(
        long id,
        String imagePath,
        String name,
        String sku,
        boolean ready,
        Long orderId,
        String imageUrl
) {}