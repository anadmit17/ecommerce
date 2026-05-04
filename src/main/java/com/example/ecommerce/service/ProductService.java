package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductRequest;
import com.example.ecommerce.exception.CategoryNotFoundException;
import com.example.ecommerce.exception.ProductNotFoundException;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product createProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategory(getCategoryById(request.getCategoryId()));

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    public Product updateProduct(Long id, ProductRequest request) {
        Product product = getProductById(id);

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategory(getCategoryById(request.getCategoryId()));

        return productRepository.save(product);
    }

    public List<Product> getProducts(String name) {
        if (name != null && !name.isBlank()) {
            return productRepository.findByNameContainingIgnoreCase(name);
        }

        return getAllProducts();
    }

    public Page<Product> getProducts(String name, Pageable pageable) {
        if (name != null && !name.isBlank()) {
            return productRepository.findByNameContainingIgnoreCase(name, pageable);
        }

        return productRepository.findAll(pageable);
    }

    private Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }
}