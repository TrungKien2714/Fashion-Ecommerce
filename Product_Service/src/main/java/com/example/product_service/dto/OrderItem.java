package com.example.product_service.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItem {
    private String id;
    private String orderId;
    private String productId;
    private Integer quantity;
    private Double priceAtPurchase;
}
