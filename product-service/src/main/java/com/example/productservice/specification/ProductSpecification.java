package com.example.productservice.specification;

import com.example.productservice.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isBlank()) {
                return null;
            }

            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Product> hasMinPrice(Double minPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
        };
    }

    public static Specification<Product> hasMaxPrice(Double maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (maxPrice == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }

    public static Specification<Product> hasCategoryId(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("category").get("id"), categoryId);
        };
    }
}