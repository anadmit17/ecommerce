package com.example.productservice.controller;

import com.example.productservice.dto.CategoryRequest;
import com.example.productservice.dto.CategoryResponse;
import com.example.productservice.model.Category;
import com.example.productservice.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories()
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryRequest request
    ) {
        Category createdCategory = categoryService.createCategory(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(createdCategory));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);

        return ResponseEntity.ok(toResponse(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request
    ) {
        Category updatedCategory = categoryService.updateCategory(id, request);

        return ResponseEntity.ok(toResponse(updatedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}