package com.example.orderservice.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    private Double totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private OrderStatus orderStatus;

    protected Order() {
    }

    public Order(
            LocalDateTime createdAt,
            Double totalPrice,
            List<OrderItem> orderItems,
            OrderStatus orderStatus
    ) {
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

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}