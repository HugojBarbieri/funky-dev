package com.funky.packageservice.model;

import com.funky.packageservice.dto.ProductDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long tiendaNubeId;
    private boolean published;
    private String canonicalUrl;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> variants;
    private String tags;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "name_id", referencedColumnName = "id")
    private ProductName name;

    public Product(ProductDTO productDTO) {
        setName(new ProductName(productDTO.name()));
        setTiendaNubeId(productDTO.id());
        setPublished(productDTO.published());
        setCanonicalUrl(productDTO.canonicalUrl());
        setVariants(productDTO.variants().stream().map(variantDTO -> {
            ProductVariant variant = new ProductVariant(variantDTO);
            variant.setProduct(this);
            return variant;}).collect(Collectors.toList()));
        setTags(productDTO.tags());
        setImages(productDTO.images().stream().map(imageDTO -> {
            ProductImage productImage = new ProductImage(imageDTO);
            productImage.setProduct(this);
            return productImage;}).collect(Collectors.toList()));

    }
}
