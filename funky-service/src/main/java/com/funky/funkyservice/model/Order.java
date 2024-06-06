package com.funky.funkyservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class Order {
    private long id;
    @JsonProperty("contact_email")
    private String contactEmail;
    @JsonProperty("contact_name")
    private String contactName;
    private String contactPhone;
    private CustomerOrder customerOrder;
    private List<ProductOrder> productOrders;
    private int number;
    private String shippingStatus;
    private String paymentStatus;
    private Date createdAt;
    private String note;
    private String ownerNote;


}
