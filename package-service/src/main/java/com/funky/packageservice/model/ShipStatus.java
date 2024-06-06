package com.funky.packageservice.model;

public enum ShipStatus {
    CORREO_ARGENTINO("correoArgentino"),
    ANDREANI("andreani"),
    OCA("oca");

    private final String name;

    ShipStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
