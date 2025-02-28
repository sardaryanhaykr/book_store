package com.bookstore.mapper;

import com.bookstore.dto.CategoryDto;
import com.bookstore.model.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements CommonMapper<CategoryEntity, CategoryDto> {
    @Override
    public CategoryEntity toEntity(CategoryDto dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());
        return entity;
    }

    @Override
    public CategoryDto toDto(CategoryEntity entity) {
        CategoryDto dto = new CategoryDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    @Override
    public void updateEntity(CategoryEntity entity, CategoryDto dto) {
        entity.setName(dto.getName());
    }
}
