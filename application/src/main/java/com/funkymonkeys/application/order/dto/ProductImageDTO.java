package com.funkymonkeys.application.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ProductImageDTO(long id, @JsonProperty("product_id") long productId,
                              String src) {
}
