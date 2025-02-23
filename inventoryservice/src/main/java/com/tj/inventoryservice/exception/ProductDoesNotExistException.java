package com.tj.inventoryservice.exception;

public class ProductDoesNotExistException extends RuntimeException {

    public ProductDoesNotExistException(String id) {
        super("Product with id %s does not exist".formatted(id));
    }
}
