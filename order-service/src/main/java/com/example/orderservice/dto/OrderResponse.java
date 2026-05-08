package com.example.orderservice.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    private Long id;
    private LocalDateTime createdAt;
    private Double totalPrice;
    private List<OrderItemResponse> orderItems;
    private OrderStatusResponse orderStatus;

    public OrderResponse(
            Long id,
            LocalDateTime createdAt,
            Double totalPrice,
            List<OrderItemResponse> orderItems,
            OrderStatusResponse orderStatus
    ) {
        this.id = id;
        this.createdAt = createdAt;
        this.totalPrice = totalPrice;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public List<OrderItemResponse> getOrderItems() {
        return orderItems;
    }

    public OrderStatusResponse getOrderStatus() {
        return orderStatus;
    }
}
