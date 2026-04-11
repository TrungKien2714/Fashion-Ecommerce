package com.example.order_service.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {

    private String id;
    private String productId;
    private String productName;
    private Integer quantity;
    private BigDecimal priceAtPurchase;
}
