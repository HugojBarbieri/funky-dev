package com.funky.funkyservice.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductDTO(
        long id,
        String depth,
        String height,
        String name,
        int quantity,
        @JsonProperty("variant_values") List<String> variantValues
) {}