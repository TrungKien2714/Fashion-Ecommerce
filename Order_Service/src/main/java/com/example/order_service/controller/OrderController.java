package com.example.order_service.controller;

import com.example.order_service.dto.BaseResponse;
import com.example.order_service.dto.request.CreateOrderReq;
import com.example.order_service.dto.response.OrderResponse;
import com.example.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<BaseResponse<OrderResponse>> createOrder(
            @Valid @RequestBody CreateOrderReq request) {
        log.info("Received create order request for userId: {}", request.getUserId());
        OrderResponse orderResponse = orderService.createOrder(request);
        BaseResponse<OrderResponse> response = new BaseResponse<>(orderResponse, "Order created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
