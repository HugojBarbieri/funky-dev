package com.funkymonkeys.application.tiendanube.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public record OrderTiendaNubeDTO(long id, @JsonProperty("contact_email") String contactEmail,
                                 @JsonProperty("contact_name") String contactName,
                                 String contactPhone,
                                 CustomerOrderTiendaNubeDTO customer,
                                 List<BasicProductTiendaNubeDTO> products,
                                 int number,
                                 @JsonProperty("shipping_status") String shippingStatus,
                                 @JsonProperty("payment_status") String paymentStatus,
                                 @JsonProperty("created_at") Date createdAt,
                                 String note,
                                 @JsonProperty("owner_note") String ownerNote)
{
}