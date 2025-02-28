package com.bookstore.service;

import com.bookstore.dto.BookDto;
import com.bookstore.dto.BookFilterDto;
import org.springframework.http.ResponseEntity;

import org.springframework.data.domain.Pageable;

public interface BookService {
    ResponseEntity<?> createBook(BookDto dto);

    ResponseEntity<?> updateBook(Long id, BookDto dto);

    ResponseEntity<?> deleteBook(Long id);

    ResponseEntity<?> findAll(Pageable pageable, BookFilterDto filter);

    ResponseEntity<?> getBook(Long id);


}