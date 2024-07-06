package com.funkymonkeys.application.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    PACKAGED("packaged"),
    UNPACKAGED("unpackaged"),
    CANCELED("canceled");

    private final String name;
}
