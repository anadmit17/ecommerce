package com.example.productservice.service;

import com.example.productservice.dto.CategoryRequest;
import com.example.productservice.exception.CategoryNotFoundException;
import com.example.productservice.model.Category;
import com.example.productservice.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());

        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }

    public Category updateCategory(Long id, CategoryRequest request) {
        Category category = getCategoryById(id);
        category.setName(request.getName());

        return categoryRepository.save(category);
    }
}
