package com.bookstore.controller;

import com.bookstore.dto.CategoryDto;
import com.bookstore.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> createCategory(CategoryDto dto) {
        return categoryService.createCategory(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, CategoryDto dto) {
        return categoryService.updateCategory(id, dto);
    }

    @GetMapping
    public ResponseEntity<?> getCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }

    @DeleteMapping("/{id}")
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }
}
