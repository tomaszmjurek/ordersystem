package com.tj.orderservice.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record Item(String productId, int quantity) {
}
