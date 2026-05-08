package com.example.orderservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public class OrderRequest {
    @NotEmpty(message = "Order items list must not be empty")
    @Valid
    private List<OrderItemRequest> orderItems;

    public OrderRequest() {
    }

    public List<OrderItemRequest> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemRequest> orderItems) {
        this.orderItems = orderItems;
    }
}
