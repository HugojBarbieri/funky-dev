package com.funky.funkyservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder
public record OrderDTO (long id,
                        @JsonProperty("contact_email") String contactEmail,
                        @JsonProperty("contact_name") String contactName,
                        String contactPhone,
                        CustomerDTO customer,
                        List<ProductDTO> products,
                        int number,
                        @JsonProperty("shipping_status") String shippingStatus,
                        @JsonProperty("payment_status") String paymentStatus,
                        @JsonProperty("created_at") Date createdAt,
                        String note,
                        @JsonProperty("owner_note") String ownerNote)
{}