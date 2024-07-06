package com.funkymonkeys.application.tiendanube.dto;

import lombok.Builder;

@Builder
public record CustomerOrderTiendaNubeDTO(
        String name
) {}