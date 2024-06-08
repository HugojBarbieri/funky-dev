package com.funky.packageservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.funky.packageservice.dto.ProductImageDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_image")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private String src;

    public ProductImage(ProductImageDTO productImageDTO) {
        setSrc(productImageDTO.src());
    }
}
