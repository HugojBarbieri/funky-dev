package com.funky.funkyservice.dto;

import lombok.Builder;

@Builder
public record CustomerDTO(
        String name
) {}