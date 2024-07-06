package com.funkymonkeys.application.tiendanube.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ProductImageTiendaNubeDTO(long id, @JsonProperty("product_id") long productId,
                                        String src) {
}
