package com.example.product_service.controller;

import com.example.product_service.dto.BaseResponse;
import com.example.product_service.dto.ProductFilter;
import com.example.product_service.dto.request.CreateProductReq;
import com.example.product_service.dto.request.LockProductReq;
import com.example.product_service.dto.UpdateProductReq;
import com.example.product_service.entity.Product;
import com.example.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/lock")
    public ResponseEntity<BaseResponse<Boolean>> lock(@RequestBody @Valid LockProductReq req){
        productService.lock(req);
        return ResponseEntity.ok(new BaseResponse<>(true,"success"));
    }

    @PostMapping("/search")
    public ResponseEntity<BaseResponse<List<Product>>> search(@RequestBody ProductFilter filter){
        List<Product> products =productService.search(filter);
        return ResponseEntity.ok(new BaseResponse<>(products,"success"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Product>> update(
            @PathVariable String id,
            @RequestBody @Valid UpdateProductReq req) {
        Product product = productService.update(id, req);
        return ResponseEntity.ok(new BaseResponse<>(product, "success"));
    }
}
