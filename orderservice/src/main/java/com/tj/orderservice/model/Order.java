package com.tj.orderservice.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import java.util.UUID;

@JsonSerialize
public record Order(UUID orderId, String customerId, List<Item> items) {

}
