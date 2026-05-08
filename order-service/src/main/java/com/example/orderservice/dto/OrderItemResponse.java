package com.example.orderservice.dto;

public class OrderItemResponse {
    private Long productId;
    private Integer quantity;
    private Double price;

    public OrderItemResponse(
            Long productId,
            Integer quantity,
            Double price
    ) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }
}
