package com.example.orderservice.exception;

public class OrderStatusNotFoundException extends RuntimeException {
    public OrderStatusNotFoundException(String name) {
        super("Order status not found: " + name);
    }
}
