package com.example.product_service.mapper;

import com.example.product_service.dto.UpdateProductReq;
import com.example.product_service.dto.request.CreateProductReq;
import com.example.product_service.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product fromCreateRequest(CreateProductReq req);
    Product fromUpdateRequest(UpdateProductReq req, @MappingTarget Product product);
}
