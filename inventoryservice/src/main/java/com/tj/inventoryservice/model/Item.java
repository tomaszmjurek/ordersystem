package com.tj.inventoryservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.UUID;

@JsonDeserialize
public record Item(UUID productId, int quantity) {
}
