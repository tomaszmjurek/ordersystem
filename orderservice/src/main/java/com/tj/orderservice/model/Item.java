package com.tj.orderservice.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.UUID;

@JsonSerialize
public record Item(UUID productId, int quantity) {
}
