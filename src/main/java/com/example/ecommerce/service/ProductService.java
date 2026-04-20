package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final List<Product> products = new ArrayList<>();
    private Long nextId = 1L;

    public ProductService() {
        products.add(new Product(nextId++, "iPhone 15", 999.99));
        products.add(new Product(nextId++, "MacBook Air M2", 1299.99));
        products.add(new Product(nextId++, "AirPods Pro", 249.99));
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(Long id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public Product createProduct(Product product) {
        product.setId(nextId++);
        products.add(product);
        return product;
    }

    public boolean deleteProduct(Long id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                products.remove(product);
                return true;
            }
        }
        return false;
    }
}
