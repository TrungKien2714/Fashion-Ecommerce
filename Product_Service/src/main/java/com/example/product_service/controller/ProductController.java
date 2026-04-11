package com.example.product_service.controller;

import com.example.product_service.dto.BaseResponse;
import com.example.product_service.dto.ProductFilter;
import com.example.product_service.dto.request.CreateProductReq;
import com.example.product_service.entity.Product;
import com.example.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {
    private final ProductService productService;
    @PostMapping
    public ResponseEntity<BaseResponse<Product>> create(@RequestBody @Valid CreateProductReq req){
        return ResponseEntity.ok(new BaseResponse<>(productService.create(req),"success"));
    }

    @PostMapping("/search")
    public ResponseEntity<BaseResponse<List<Product>>> search(@RequestBody ProductFilter filter){
        List<Product> products =productService.search(filter);
        return ResponseEntity.ok(new BaseResponse<>(products,"success"));
    }
}
