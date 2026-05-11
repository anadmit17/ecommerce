package com.example.productservice.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(Long productId) {
        super("Not enough stock for product with id: " + productId);
    }
}