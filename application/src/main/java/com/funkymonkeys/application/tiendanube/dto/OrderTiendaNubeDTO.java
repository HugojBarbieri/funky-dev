package com.funkymonkeys.application.tiendanube.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class OrderTiendaNubeDTO {

    private long id;
    @JsonProperty("contact_email")
    private String contactEmail;
    @JsonProperty("contact_name")
    private String contactName;
    private String contactPhone;
    private CustomerOrderTiendaNubeDTO customer;
    private List<BasicProductTiendaNubeDTO> products;
    private int number;
    @JsonProperty("shipping_status")
    private String shippingStatus;
    @JsonProperty("status")
    private String orderStatus;
    @JsonProperty("payment_status")
    private String paymentStatus;
    @JsonProperty("created_at")
    private Date createdAt;
    private String note;
    @JsonProperty("owner_note")
    private String ownerNote;
    private boolean ready;
}