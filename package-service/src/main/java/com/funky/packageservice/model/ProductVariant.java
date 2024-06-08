package com.funky.packageservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.funky.packageservice.dto.ProductVariantDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_variant")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long imageId;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int position;
    private String price;
    private int stock;
    private String sku;

    public ProductVariant(ProductVariantDTO productVariantDTO) {
        setImageId(productVariantDTO.imageId());
        setPosition(productVariantDTO.position());
        setPrice(productVariantDTO.price());
        setStock(productVariantDTO.stock());
        setSku(productVariantDTO.sku());
    }
}
