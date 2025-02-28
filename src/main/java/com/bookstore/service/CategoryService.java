package com.bookstore.service;

import com.bookstore.dto.CategoryDto;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity<?> createCategory(CategoryDto dto);

    ResponseEntity<?> updateCategory(Long id, CategoryDto dto);

    ResponseEntity<?> deleteCategory(Long id);

    ResponseEntity<?> getCategory(Long id);

    ResponseEntity<?> getCategories();
}
