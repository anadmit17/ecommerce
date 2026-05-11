package com.example.orderservice.service;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.ProductResponse;
import com.example.orderservice.dto.QuantityRequest;
import com.example.orderservice.exception.NotEnoughStockException;
import com.example.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.exception.OrderStatusNotFoundException;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderItem;
import com.example.orderservice.model.OrderStatus;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.repository.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class OrderService {
    @Value("${services.product-service.url}")
    private String productServiceUrl;

    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final RestClient restClient;

    public OrderService(
            OrderRepository orderRepository,
            OrderStatusRepository orderStatusRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.restClient = RestClient.create();
    }

    public Order createOrder(OrderRequest orderRequest) {
        OrderStatus createdStatus = orderStatusRepository.findByName("CREATED")
                .orElseThrow(() -> new OrderStatusNotFoundException("CREATED"));

        List<OrderItem> orderItems = orderRequest.getOrderItems()
                .stream()
                .map(itemRequest -> {
                    ProductResponse productResponse = restClient.get()
                            .uri(productServiceUrl + "/products/" + itemRequest.getProductId())
                            .retrieve()
                            .body(ProductResponse.class);

                    if (itemRequest.getQuantity() > productResponse.getStockQuantity()) {
                        throw new NotEnoughStockException(productResponse.getId());
                    }

                    OrderItem orderItem = new OrderItem();
                    orderItem.setProductId(productResponse.getId());
                    orderItem.setQuantity(itemRequest.getQuantity());
                    orderItem.setPrice(productResponse.getPrice());

                    return orderItem;
                })
                .toList();

        double totalPrice = orderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderStatus(createdStatus);
        orderItems.forEach(item -> item.setOrder(order));
        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        orderItems.forEach(
                item -> restClient.patch()
                        .uri(productServiceUrl + "/products/" + item.getProductId() + "/stock/decrease")
                        .contentType(APPLICATION_JSON)
                        .body(new QuantityRequest(item.getQuantity()))
                        .retrieve()
                        .toBodilessEntity()
        );

        return savedOrder;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
