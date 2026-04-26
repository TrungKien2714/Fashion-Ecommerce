package com.example.order_service.mapper;

import com.example.order_service.dto.request.OrderItemReq;
import com.example.order_service.dto.response.OrderItemResponse;
import com.example.order_service.dto.response.OrderResponse;
import com.example.order_service.entity.Order;
import com.example.order_service.entity.OrderItem;
import com.example.order_service.events.OrderCreatedEvent;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderItemResponse toOrderItemResponse(OrderItem orderItem);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "productName", ignore = true)
    @Mapping(target = "priceAtPurchase", ignore = true)
    OrderItem toOrderItem(OrderItemReq req);

    @Mapping(source = "orderItems", target = "orderItems")
    OrderResponse toOrderResponse(Order order);
    @BeanMapping(builder = @Builder(disableBuilder = true))
    OrderCreatedEvent toEvent(Order order);
}
