package com.funky.packageservice.model;

import com.funky.packageservice.dto.ProductNameDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_name")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String es;
    @OneToOne(mappedBy = "name")
    private Product product;

    public ProductName(ProductNameDTO productNameDTO) {
        setEs(productNameDTO.es());
    }
}
