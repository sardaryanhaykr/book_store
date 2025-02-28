package com.bookstore.mapper;

import com.bookstore.dto.PublisherDto;
import com.bookstore.model.PublisherEntity;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper implements CommonMapper<PublisherEntity, PublisherDto> {

    @Override
    public PublisherEntity toEntity(PublisherDto dto) {
        PublisherEntity entity = new PublisherEntity();
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        return entity;
    }

    @Override
    public PublisherDto toDto(PublisherEntity entity) {
        PublisherDto dto = new PublisherDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        return dto;
    }

    @Override
    public void updateEntity(PublisherEntity entity, PublisherDto dto) {
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
    }
}
