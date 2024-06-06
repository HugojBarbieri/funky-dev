package com.funky.packageservice.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ProductDTO (long id, ProductNameDTO name, boolean published, String canonicalUrl,
                          List<ProductVariantDTO> variants, String tags, List<ProductImageDTO> images){
}
