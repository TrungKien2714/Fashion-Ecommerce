package com.example.product_service.service.impl;

import com.example.product_service.dto.ProductFilter;
import com.example.product_service.dto.request.CreateProductReq;
import com.example.product_service.entity.Product;
import com.example.product_service.mapper.ProductMapper;
import com.example.product_service.repository.CategoryRepository;
import com.example.product_service.repository.ProductRepository;
import com.example.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    @Override
    public Product create(CreateProductReq req) {
        var existedCategory=categoryRepository.findById(req.getCategoryId());
        if(existedCategory.isEmpty()){
            throw new RuntimeException("Category not found");
        }
        Product product = productMapper.fromCreateRequest(req);
        product.setCreatedDate(Instant.now());
        product.setDeleted(false);
        product.setLastModifiedDate(Instant.now());
        return productRepository.save(product);
        }
    @Override
    public List<Product> search(ProductFilter filter) {
        return productRepository.findByIdIn(filter.getIds());
     }
}
