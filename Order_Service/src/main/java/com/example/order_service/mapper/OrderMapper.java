package com.example.order_service.mapper;

import com.example.order_service.dto.request.OrderItemReq;
import com.example.order_service.dto.response.OrderItemResponse;
import com.example.order_service.dto.response.OrderResponse;
import com.example.order_service.entity.Order;
import com.example.order_service.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderItemResponse toOrderItemResponse(OrderItem orderItem);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem toOrderItem(OrderItemReq req);

    @Mapping(source = "orderItems", target = "orderItems")
    OrderResponse toOrderResponse(Order order);
}
