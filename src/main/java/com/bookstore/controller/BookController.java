package com.bookstore.controller;

import com.bookstore.dto.BookDto;
import com.bookstore.dto.BookFilterDto;
import com.bookstore.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody BookDto dto) {
        return bookService.createBook(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, BookDto dto) {
        return bookService.updateBook(id, dto);
    }

    @GetMapping
    public ResponseEntity<?> getBooks(
            @RequestParam(required = false) Optional<Integer> page,
            @RequestParam(required = false) Optional<Integer> size,
            @RequestParam(required = false) Optional<String> sortBy,
            @RequestParam(required = false) Boolean isDesc,
            @ModelAttribute @Valid BookFilterDto filter) {
        Sort sort = Boolean.TRUE.equals(isDesc) ? Sort.by(sortBy.orElse("id")).descending() : Sort.by(sortBy.orElse("id")).ascending();
        Pageable paging = PageRequest.of(page.orElse(0), size.orElse(20), sort);

        return bookService.findAll(paging, filter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }
}
