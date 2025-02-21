package com.tj.orderservice.dto;

import com.tj.orderservice.model.Item;

import java.util.List;

public record CreateOrderRequestDTO(String customerId, List<Item> items) {
}
