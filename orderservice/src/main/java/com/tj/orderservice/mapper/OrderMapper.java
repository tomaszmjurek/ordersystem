package com.tj.orderservice.mapper;

import com.tj.orderservice.dto.CreateOrderRequestDTO;
import com.tj.orderservice.dto.OrderResponseDTO;
import com.tj.orderservice.model.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderMapper {

    public OrderResponseDTO map(Order order) {
        return new OrderResponseDTO(order.customerId(), order.orderId().toString());
    }

    public Order map(CreateOrderRequestDTO requestDTO) {
        return new Order(UUID.randomUUID(), requestDTO.customerId(), requestDTO.items());
    }

}
