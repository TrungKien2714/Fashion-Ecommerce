package com.example.order_service.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private String id;
    private String userId;
    private String status;
    private BigDecimal totalPrice;
    private String shippingAddress;
    private String note;
    private Instant createdDate;
    private Instant lastModifiedDate;
    private List<OrderItemResponse> orderItems;
}
