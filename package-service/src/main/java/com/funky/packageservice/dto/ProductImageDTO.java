package com.funky.packageservice.dto;

import lombok.Builder;

@Builder
public record ProductImageDTO(long id, long productId, String src) {
}
