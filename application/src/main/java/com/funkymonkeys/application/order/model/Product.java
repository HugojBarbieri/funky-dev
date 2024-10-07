package com.funkymonkeys.application.order.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long tiendaNubeId;
    private String imagePath;
    private String name;
    private String sku;
    private boolean ready;
    private Long orderId;
    private int orderNumber;
    private String imageUrl;
    private String barcode;
    @Column(name = "check_status")
    private int check = 0;
    private int quantity;
}
