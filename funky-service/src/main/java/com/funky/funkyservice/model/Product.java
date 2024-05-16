package com.funky.funkyservice.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Product {
    private long id;
    private String depth;
    private String height;
    private String name;
    private int quantity;
    private List<String> variantValues;

}
