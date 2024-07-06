package com.funkymonkeys.application.order.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long tiendaNubeId;
    private int number;
    private OrderStatus orderStatus;
    private ShipStatus shipStatus;
    private String customer;
    @OneToMany(mappedBy = "orderId")
    private List<Product> products;
}
