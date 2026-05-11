package com.example.productservice.controller;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.dto.QuantityRequest;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    private ProductResponse toResponse(Product product) {
        Category category = product.getCategory();

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStockQuantity(),
                category != null ? category.getId() : null,
                category != null ? category.getName() : null
        );
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productPage = productService.getProducts(
                name,
                minPrice,
                maxPrice,
                categoryId,
                pageable
        );

        List<ProductResponse> products = productPage
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(toResponse(product));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        Product createdProduct = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(createdProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest productRequest
    ) {
        Product updatedProduct = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(toResponse(updatedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/stock/decrease")
    public ResponseEntity<ProductResponse> decreaseStockQuantity(
            @PathVariable Long id,
            @RequestBody QuantityRequest quantityRequest
    ) {
        Product updatedProduct = productService.decreaseStockQuantity(id, quantityRequest);
        return ResponseEntity.ok(toResponse(updatedProduct));
    }

    @PatchMapping("/{id}/stock/increase")
    public ResponseEntity<ProductResponse> increaseStockQuantity(
            @PathVariable Long id,
            @RequestBody QuantityRequest quantityRequest
    ) {
        Product updatedProduct = productService.increaseStockQuantity(id, quantityRequest);
        return ResponseEntity.ok(toResponse(updatedProduct));
    }
}