package com.example.productservice.dto;

import jakarta.validation.constraints.*;

public class ProductRequest {
    @NotBlank(message = "Product name is required")
    private String name;

    @Positive(message = "Product price must be greater than 0")
    private double price;

    @PositiveOrZero(message = "Stock quantity must be zero or greater")
    private int stockQuantity;

    @NotNull(message = "Category id is required")
    private Long categoryId;

    public ProductRequest() {
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
