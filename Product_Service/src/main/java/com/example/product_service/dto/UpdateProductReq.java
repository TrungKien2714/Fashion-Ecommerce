package com.example.product_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductReq {
    @NotEmpty
    private String name;

    @NotNull
    @Positive
    private Integer price;

    @NotNull
    @Positive
    private Integer stock;

    @NotEmpty
    private String categoryId;
}
