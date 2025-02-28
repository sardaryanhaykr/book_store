package com.bookstore.mapper;

public interface CommonMapper <E, D>{
    E toEntity(D dto);
    D toDto(E entity);
    void updateEntity(E entity, D dto);
}
