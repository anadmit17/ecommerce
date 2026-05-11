package com.example.orderservice.dto;

public class ProductResponse {
    private Long id;
    private String name;
    private Double price;
    private Integer stockQuantity;

    public ProductResponse(
            Long id,
            String name,
            Double price,
            Integer stockQuantity
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }
}
