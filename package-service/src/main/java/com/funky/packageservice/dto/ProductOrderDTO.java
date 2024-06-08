package com.funky.packageservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOrderDTO {
    private long id;
    private String depth;
    private String height;
    private String name;
    private String sku;
    private int quantity;
    private List<String> variantValues;
    private ProductOrderImageDTO image;

}
