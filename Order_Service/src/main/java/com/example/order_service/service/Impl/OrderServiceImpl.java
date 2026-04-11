package com.example.order_service.service.Impl;

import com.example.order_service.clients.ProductClient;
import com.example.order_service.dto.ProductDTO;
import com.example.order_service.dto.enums.OrderStatus;
import com.example.order_service.dto.request.CreateOrderReq;
import com.example.order_service.dto.request.OrderItemReq;
import com.example.order_service.dto.request.ProductFilter;
import com.example.order_service.dto.response.OrderResponse;
import com.example.order_service.entity.Order;
import com.example.order_service.entity.OrderItem;
import com.example.order_service.exception.BusinessException;
import com.example.order_service.mapper.OrderMapper;
import com.example.order_service.repository.OrderItemRepository;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemRepository  orderItemRepository;
    private final ProductClient productClient;

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderReq request) {
        List<String> productIds = request.getOrderItems().stream().map(OrderItemReq::getProductId).toList();
        List<ProductDTO> products=productClient.getProductByIds(new ProductFilter(productIds));

        Map<String,ProductDTO> productPriceMap= new HashMap<>();
        products.forEach(productDTO -> productPriceMap.put(productDTO.getId(),productDTO));
        Order order=new Order();
        order.setUserId(request.getUserId());
        order.setShippingAddress(request.getShippingAddress());
        order.setNote(request.getNote());
        order.setStatus(OrderStatus.NEW.name());
        order.setTotalPrice(0);
        Order saveOrder = orderRepository.save(order);
        int totalAmount=0;
        List<OrderItem>  orderItems=new ArrayList<>();
        for(OrderItemReq orderItemReq : request.getOrderItems()){
            ProductDTO productDTO = productPriceMap.get(orderItemReq.getProductId());
            if(productDTO==null){
                throw new BusinessException("Product not found");
            }
            if(orderItemReq.getQuantity()>productDTO.getStock()){
                throw new BusinessException("Số lượng sản phẩm " + productDTO.getName() + " vượt quá tồn kho");
            }
            Integer price= productDTO.getPrice();
            OrderItem orderItem=new OrderItem();
            orderItem.setId(saveOrder.getId());
            orderItem.setOrder(saveOrder);
            orderItem.setProductId(orderItemReq.getProductId());
            orderItem.setProductName(productDTO.getName());
            orderItem.setPriceAtPurchase(price);
            orderItem.setQuantity(orderItemReq.getQuantity());
            orderItems.add(orderItem);
            totalAmount+=price*orderItemReq.getQuantity();
        }
        orderItemRepository.saveAll(orderItems);
        saveOrder.setTotalPrice(totalAmount);
        saveOrder.setOrderItems(orderItems);
        Order updatedOrder = orderRepository.save(saveOrder);
        return orderMapper.toOrderResponse(updatedOrder);
    }


}
