package com.example.orderservice.service;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.exception.OrderStatusNotFoundException;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderItem;
import com.example.orderservice.model.OrderStatus;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.repository.OrderStatusRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;

    public OrderService(
            OrderRepository orderRepository,
            OrderStatusRepository orderStatusRepository

    ) {
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
    }

    public Order createOrder(OrderRequest orderRequest) {
        OrderStatus createdStatus = orderStatusRepository.findByName("CREATED")
                .orElseThrow(() -> new OrderStatusNotFoundException("CREATED"));

        List<OrderItem> orderItems = orderRequest.getOrderItems()
                .stream()
                .map(itemRequest -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProductId(itemRequest.getProductId());
                    orderItem.setQuantity(itemRequest.getQuantity());
                    orderItem.setPrice(0.0);

                    return orderItem;
                })
                .toList();

        double totalPrice = orderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderStatus(createdStatus);
        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
