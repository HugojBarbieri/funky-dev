package com.funky.packageservice.dto;

import lombok.Builder;

@Builder
public record ProductVariantDTO(long id, long imageId, long productId, int position, String price,
                                int stock, String sku) {

}