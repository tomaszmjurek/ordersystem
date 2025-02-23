package com.tj.inventoryservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;
import java.util.UUID;

@JsonDeserialize
public record Order(UUID orderId, String customerId, List<Item> items) {

}
