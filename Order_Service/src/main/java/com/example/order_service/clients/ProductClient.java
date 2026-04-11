package com.example.order_service.clients;

import com.example.order_service.dto.ProductDTO;
import com.example.order_service.dto.request.ProductFilter;

import java.util.List;

public interface ProductClient {
    List<ProductDTO> getProductByIds(ProductFilter filter);
}
