package com.funkymonkeys.application.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.funkymonkeys.application.tiendanube.dto.ProductNameTiendaNubeDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductDTO (long id, ProductNameTiendaNubeDTO name, boolean published,
                          String canonicalUrl,
                          List<ProductVariantDTO> variants, String tags, int quantity,
                          List<ProductImageDTO> images){
}

