package com.funky.funkyservice.model;


import java.util.List;

public class ProductDTO {
    private long id;
    private String depth;
    private String height;
    private String name;
    private int quantity;
    private List<String> variantValues;

    // Constructor, getters, and setters
    public ProductDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<String> getVariantValues() {
        return variantValues;
    }

    public void setVariantValues(List<String> variantValues) {
        this.variantValues = variantValues;
    }
}
