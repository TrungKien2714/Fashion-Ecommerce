package com.example.order_service.clients.impl;

import com.example.order_service.clients.ProductClient;
import com.example.order_service.dto.BaseResponse;
import com.example.order_service.dto.ProductDTO;
import com.example.order_service.dto.request.ProductFilter;
import com.example.order_service.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductClientImpl implements ProductClient {
    private final WebClient.Builder webClientBuilder;
    @Override
    public List<ProductDTO> getProductByIds(ProductFilter filter) {
        BaseResponse<List<ProductDTO>> response=webClientBuilder.build()
                .post()
                .uri("http://product-service/v1/products/search")
                .bodyValue(filter)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<ProductDTO>>>() {
                })
                .block();
        if(response ==null || response.getData() ==null){
            throw new BusinessException("Không thể lấy thông tin sản phẩm từ Product Service");
        }
        return response.getData();
    }
}
