package com.funkymonkeys.application.tiendanube.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record BasicProductTiendaNubeDTO(
        long id,
        String depth,
        String height,
        String name,
        int quantity,
        String sku,
        @JsonProperty("variant_values") List<String> variantValues,
        ProductOrderImageTiendaNubeDTO image
) {}