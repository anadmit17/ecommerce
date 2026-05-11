package com.example.orderservice.dto;

public class QuantityRequest {
    private Integer quantity;

    public QuantityRequest(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
