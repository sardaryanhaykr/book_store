package com.bookstore.controller;

import com.bookstore.dto.CategoryDto;
import com.bookstore.service.CategoryServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryServiceImpl categoryServiceImpl;

    @PostMapping
    public ResponseEntity<?> createCategory(CategoryDto dto) {
        return categoryServiceImpl.createCategory(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, CategoryDto dto) {
        return categoryServiceImpl.updateCategory(id, dto);
    }

    @GetMapping
    public ResponseEntity<?> getCategories() {
        return categoryServiceImpl.getCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id) {
        return categoryServiceImpl.getCategory(id);
    }

    @DeleteMapping("/{id}")
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        return categoryServiceImpl.deleteCategory(id);
    }
}
