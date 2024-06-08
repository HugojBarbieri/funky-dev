package com.funky.packageservice.model;

public enum OrderStatus {
    PACKAGED("packaged"),
    UNPACKAGED("unpackaged");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
