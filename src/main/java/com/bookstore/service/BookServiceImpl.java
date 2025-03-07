package com.bookstore.service;

import com.bookstore.dto.BookDto;
import com.bookstore.dto.BookFilterDto;
import com.bookstore.dto.PageDTO;
import com.bookstore.exception.DuplicateResourceException;
import com.bookstore.exception.IllegalArgumentExceptionCustom;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.mapper.BookMapper;
import com.bookstore.model.BookEntity;
import com.bookstore.model.CategoryEntity;
import com.bookstore.model.PublisherEntity;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CategoryRepository;
import com.bookstore.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<?> createBook(BookDto dto) {
        validateAdd(dto);
        BookEntity book = new BookEntity();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPrice(dto.getPrice());
        book.setIsbn(dto.getIsbn());
        book.setPublishedDate(dto.getPublishedDate());
        PublisherEntity publisher = publisherRepository.findById(dto.getPublisherId()).orElseThrow(() -> new ResourceNotFoundException("Publisher"));
        book.setPublisher(publisher);
        CategoryEntity category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category"));
        book.setCategory(category);
        book = bookRepository.save(book);
        dto.setId(book.getId());
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<?> updateBook(Long id, BookDto dto) {
        BookEntity book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book"));
        validateUpdate(dto, book);
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        bookRepository.save(book);
        dto.setId(id);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<?> deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book");
        }
        bookRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll(Pageable pageable, BookFilterDto filter) {
        Page<BookEntity> bookEntityPage = bookRepository.getDataWithPagination(pageable, filter);
        List<BookDto> bookDtos = bookEntityPage.map(bookMapper::toDto).getContent();
        PageDTO<BookDto> bookDtoPage = new PageDTO<>(bookDtos, bookEntityPage.getNumber(), bookEntityPage.getSize(), bookEntityPage.getTotalElements());
        return ResponseEntity.ok(bookDtoPage);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getBook(Long id) {
        Optional<BookEntity> optionalBook = bookRepository.findByIdEager(id);
        if (optionalBook.isEmpty()) {
            throw new ResourceNotFoundException("Book");
        }
        return ResponseEntity.ok(bookMapper.toDto(optionalBook.get()));
    }

    private void validateAdd(BookDto dto) {
        validateBookDto(dto);

        if (bookRepository.isBookExist(dto.getIsbn())) {
            throw new DuplicateResourceException("Book with ISBN: " + dto.getIsbn());
        }
    }

    private void validateUpdate(BookDto dto, BookEntity entity) {
        validateBookDto(dto);
        if (!Objects.equals(entity.getIsbn(), dto.getIsbn()) && bookRepository.isBookExist(dto.getIsbn())) {
            throw new DuplicateResourceException("Book with ISBN: " + dto.getIsbn());
        }
    }

    private void validateBookDto(BookDto dto) {
        if (dto.getPublisherId() == null) {
            throw new IllegalArgumentExceptionCustom("Publisher ID is required");
        }
        if (!publisherRepository.existsById(dto.getPublisherId())) {
            throw new ResourceNotFoundException("Publisher");
        }
        if (dto.getCategoryId() == null) {
            throw new IllegalArgumentExceptionCustom("Category ID is required");
        }
        if (!categoryRepository.existsById(dto.getCategoryId())) {
            throw new ResourceNotFoundException("Category");
        }

        if (dto.getIsbn() == null) {
            throw new IllegalArgumentExceptionCustom("ISBN is required");
        }
    }
}
