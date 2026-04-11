package com.example.order_service.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderReq {

    @NotBlank(message = "userId is required")
    private String userId;

    @NotBlank(message = "shippingAddress is required")
    private String shippingAddress;

    private String note;

    @NotEmpty(message = "Order must have at least one item")
    @Valid
    private List<OrderItemReq> orderItems;
}
