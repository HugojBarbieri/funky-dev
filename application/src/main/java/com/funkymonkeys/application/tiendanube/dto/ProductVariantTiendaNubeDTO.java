package com.funkymonkeys.application.tiendanube.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ProductVariantTiendaNubeDTO(long id, @JsonProperty("image_id") long imageId,
                                          @JsonProperty("product_id") long productId, int position,
                                          String price, int stock, String sku) {

}