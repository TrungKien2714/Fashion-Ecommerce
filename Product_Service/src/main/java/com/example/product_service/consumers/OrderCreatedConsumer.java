package com.example.product_service.consumers;

import com.example.product_service.consumers.dto.Order;
import com.example.product_service.dto.OrderCreatedEvent;
import com.example.product_service.dto.request.LockProductItem;
import com.example.product_service.dto.request.LockProductReq;
import com.example.product_service.service.ProductService;
import com.fasterxml.jackson.core.JacksonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class OrderCreatedConsumer {
    private final ObjectMapper objectMapper;
    private final ProductService productService;
    @KafkaListener(topics = "order-created")
    @RetryableTopic(
            attempts = "4",
            backoff = @Backoff(delay = 2000, multiplier = 2.0),
            exclude = {NullPointerException.class, IllegalArgumentException.class}
    )
    public void handleOrderCreatedEvent(String orderString) throws JacksonException {
        OrderCreatedEvent orderCreatedEvent=objectMapper.readValue(orderString, OrderCreatedEvent.class);
        log.info("Received order created event: {}", orderCreatedEvent);
        List<LockProductItem> lockProductItems=new ArrayList<>();
        orderCreatedEvent.getOrderItems().forEach(orderItem -> {
            LockProductItem lockProductItem=new LockProductItem();
            lockProductItem.setId(orderItem.getProductId());
            lockProductItem.setQuantity(orderItem.getQuantity());
            lockProductItems.add(lockProductItem);
        });
        LockProductReq lockProductReq=new LockProductReq();
        lockProductReq.setItems(lockProductItems);
        productService.lock(lockProductReq);
        log.info("Product locked successfully");
    }
}
