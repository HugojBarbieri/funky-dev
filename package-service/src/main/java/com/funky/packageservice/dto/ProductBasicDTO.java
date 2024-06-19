package com.funky.packageservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductBasicDTO {

    private long id;
    private String imagePath;
    private String name;
    private String sku;
    private boolean ready;
    private Long orderId;
}
