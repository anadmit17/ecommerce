package com.example.orderservice.dto;

import jakarta.validation.constraints.*;

public class OrderStatusRequest {
    @NotBlank(message = "Order status name is required")
    @Size(max = 50, message = "Status name must be less than 50 characters")
    private String name;

    public OrderStatusRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
