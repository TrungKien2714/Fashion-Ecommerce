package com.example.product_service.consumers.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    private String id;
    private String userId;
    private String status;
    private Double totalPrice;
}
