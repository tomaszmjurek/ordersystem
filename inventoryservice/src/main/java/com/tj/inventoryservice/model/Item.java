package com.tj.inventoryservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.UUID;

@JsonDeserialize
public record Item(UUID productId, int quantity) {
}
