package com.funky.funkyservice.dto;

import lombok.Builder;

@Builder
public record CustomerOrderDTO(
        String name
) {}