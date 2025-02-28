package com.bookstore.mapper;

import com.bookstore.dto.BookDto;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.model.BookEntity;
import com.bookstore.model.CategoryEntity;
import com.bookstore.model.PublisherEntity;
import com.bookstore.repository.CategoryRepository;
import com.bookstore.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class BookMapper implements CommonMapper<BookEntity, BookDto> {
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public BookEntity toEntity(BookDto dto) {
        BookEntity entity = new BookEntity();
        entity.setTitle(dto.getTitle());
        entity.setAuthor(dto.getAuthor());
        entity.setPrice(dto.getPrice());
        entity.setIsbn(dto.getIsbn());
        entity.setPublishedDate(dto.getPublishedDate());
        return entity;
    }

    @Override
    public BookDto toDto(BookEntity entity) {
        BookDto dto = new BookDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setAuthor(entity.getAuthor());
        dto.setPrice(entity.getPrice());
        dto.setIsbn(entity.getIsbn());
        dto.setPublishedDate(entity.getPublishedDate());
        if (entity.getPublisher() != null) {
            dto.setPublisherId(entity.getPublisher().getId());
        }
        if (entity.getCategory() != null) {
            dto.setCategoryId(entity.getCategory().getId());
        }
        return dto;
    }

    @Override
    public void updateEntity(BookEntity entity, BookDto dto) {
        entity.setTitle(dto.getTitle());
        entity.setAuthor(dto.getAuthor());
        entity.setPrice(dto.getPrice());
        entity.setIsbn(dto.getIsbn());
        entity.setPublishedDate(dto.getPublishedDate());
        if (dto.getPublisherId() != null && !Objects.equals(dto.getPublisherId(), entity.getPublisher().getId())) {
            PublisherEntity publisherEntity = publisherRepository.findById(dto.getPublisherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Publisher"));
            entity.setPublisher(publisherEntity);
        }

        if (dto.getCategoryId() != null && !Objects.equals(dto.getCategoryId(), entity.getCategory().getId())) {
            CategoryEntity categoryEntity = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category"));
            entity.setCategory(categoryEntity);
        }
    }
}
