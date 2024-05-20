package com.funky.packageservice.model;

public enum PaymentStatus {
    PENDING("pending"),
    PAID("paid");

    private final String name;

    PaymentStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
