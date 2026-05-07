package com.example.product_service.service;

import com.example.product_service.dto.ProductFilter;
import com.example.product_service.dto.UpdateProductReq;
import com.example.product_service.dto.request.CreateProductReq;
import com.example.product_service.dto.request.LockProductReq;
import com.example.product_service.entity.Product;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ProductService {
    Product create(@RequestBody @Valid CreateProductReq req);
    List<Product> search(ProductFilter filter);
    void lock(LockProductReq req);
    Product update(String id, UpdateProductReq updateProductReq);
}
