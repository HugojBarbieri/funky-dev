package com.funky.packageservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private long id;
    @JsonProperty("contact_email")
    private String contactEmail;
    @JsonProperty("contact_name")
    private String contactName;
    private String contactPhone;
    private CustomerOrderDTO customer;
    private List<ProductOrderDTO> products;
    @JsonProperty("number")
    private int number;
    @JsonProperty("shipping_status")
    private String shippingStatus;
    @JsonProperty("payment_status")
    private String paymentStatus;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("note")
    private String note;
    @JsonProperty("owner_note")
    private String ownerNote;
}
