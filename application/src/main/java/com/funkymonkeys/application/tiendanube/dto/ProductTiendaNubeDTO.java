package com.funkymonkeys.application.tiendanube.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductTiendaNubeDTO(long id, ProductNameTiendaNubeDTO name, boolean published,
                                   @JsonProperty("canonical_url") String canonicalUrl, int quantity,
                                   List<ProductVariantTiendaNubeDTO> variants, String tags, String barcode,
                                   List<ProductImageTiendaNubeDTO> images, int check){
}

