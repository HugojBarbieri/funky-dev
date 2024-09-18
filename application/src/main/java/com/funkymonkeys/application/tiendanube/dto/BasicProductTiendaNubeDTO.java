package com.funkymonkeys.application.tiendanube.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BasicProductTiendaNubeDTO {
    private long id;
    private String depth;
    private String height;
    private String name;
    private int quantity;
    private String sku;

    @JsonProperty("variant_values")
    private List<String> variantValues;

    private ProductOrderImageTiendaNubeDTO image;
    private boolean ready;
    private String barcode;
    private int check;
}