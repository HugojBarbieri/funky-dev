package com.funky.packageservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductDTO (long id, ProductNameDTO name, boolean published, @JsonProperty("canonical_url")  String canonicalUrl,
                          List<ProductVariantDTO> variants, String tags, List<ProductImageDTO> images){
}
