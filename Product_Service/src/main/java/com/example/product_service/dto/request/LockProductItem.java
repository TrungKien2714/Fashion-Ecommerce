package com.example.product_service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LockProductItem {
    @NotEmpty
    private String id;
    @Positive
    @NotNull
    private Integer quantity;
}
