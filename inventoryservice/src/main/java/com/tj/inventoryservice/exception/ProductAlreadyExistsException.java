package com.tj.inventoryservice.exception;

public class ProductAlreadyExistsException extends RuntimeException {

    public ProductAlreadyExistsException(String name) {
        super("Product with this name %s already exists".formatted(name));
    }
}
