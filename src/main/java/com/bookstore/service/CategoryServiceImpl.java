package com.bookstore.service;

import com.bookstore.dto.CategoryDto;
import com.bookstore.exception.DuplicateResourceException;
import com.bookstore.exception.IllegalArgumentExceptionCustom;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.mapper.CategoryMapper;
import com.bookstore.model.CategoryEntity;
import com.bookstore.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public ResponseEntity<?> createCategory(CategoryDto dto) {
        validateAdd(dto);
        CategoryEntity category = categoryMapper.toEntity(dto);
        category = categoryRepository.save(category);
        dto.setId(category.getId());
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<?> updateCategory(Long id, CategoryDto dto) {
        validateDto(dto);
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category"));
        validateUpdate(dto, category);
        categoryMapper.updateEntity(category, dto);
        categoryRepository.save(category);
        dto.setId(category.getId());
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<?> deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category");
        }
        categoryRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> getCategory(Long id) {
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category"));
        CategoryDto dto = categoryMapper.toDto(category);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<?> getCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        if (CollectionUtils.isEmpty(categories)) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<CategoryDto> categoryDtos = categories.stream().map(categoryMapper::toDto).toList();
        return ResponseEntity.ok(categoryDtos);
    }

    private void validateAdd(CategoryDto dto) {
        validateDto(dto);
        if (categoryRepository.existsByName(dto.getName())) {
            throw new DuplicateResourceException("Category with name " + dto.getName());
        }
    }

    private void validateUpdate(CategoryDto dto, CategoryEntity entity) {
        if (!Objects.equals(entity.getName(), dto.getName()) && categoryRepository.existsByName(dto.getName())) {
            throw new DuplicateResourceException("Category with name " + dto.getName());
        }
    }

    private void validateDto(CategoryDto dto) {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new IllegalArgumentExceptionCustom("Category name cannot be empty");
        }
    }
}
