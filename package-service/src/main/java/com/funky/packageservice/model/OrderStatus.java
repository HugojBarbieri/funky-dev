package com.funky.packageservice.model;

public enum OrderStatus {
    ON_HOLD("on_hold"),
    ACTIVE("active"),
    INACTIVE("inactive"),
    FINISHED("finished"),
    CANCEL("cancel");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
