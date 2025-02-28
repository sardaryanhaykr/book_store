package com.bookstore;

import com.bookstore.dto.BookDto;
import com.bookstore.mapper.BookMapper;
import com.bookstore.model.BookEntity;
import com.bookstore.model.CategoryEntity;
import com.bookstore.model.PublisherEntity;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CategoryRepository;
import com.bookstore.repository.PublisherRepository;
import com.bookstore.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private BookEntity book;
    private BookEntity updateBook;
    private BookDto bookDto;

    @BeforeEach
    public void setUp() {
        PublisherEntity publisher = new PublisherEntity();
        publisher.setId(1L);
        publisher.setName("Test Publisher");
        publisher.setAddress("Test Address");
        publisher.setPhone("1234567890");
        publisher.setEmail("test123@mail.com");

        CategoryEntity category = new CategoryEntity();
        category.setId(1L);
        category.setName("Test Category");

        book = new BookEntity();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPrice(100.0);
        book.setIsbn("1234567890");
        book.setPublisher(publisher);
        book.setCategory(category);

        updateBook = new BookEntity();
        updateBook.setId(1L);
        updateBook.setTitle("Test Book");
        updateBook.setAuthor("Test Author");
        updateBook.setPrice(100.0);
        updateBook.setIsbn("1234567890");
        updateBook.setPublisher(publisher);
        updateBook.setCategory(category);

        bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Test Book");
        bookDto.setAuthor("Test Author");
        bookDto.setPrice(100.0);
        bookDto.setIsbn("1234567890");
        bookDto.setPublisherId(1L);
        bookDto.setCategoryId(1L);
    }

    @Test
    void testCreateBook() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Test Book");
        bookDto.setAuthor("Test Author");
        bookDto.setPrice(100.0);
        bookDto.setIsbn("1234567890");
        bookDto.setPublisherId(1L);
        bookDto.setCategoryId(1L);

        when(bookRepository.save(any(BookEntity.class))).thenReturn(new BookEntity());
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(new PublisherEntity()));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(new CategoryEntity()));
        ResponseEntity<?> result = bookService.createBook(bookDto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isInstanceOf(BookDto.class);

        BookDto createdBook = (BookDto) result.getBody();
        assertThat(createdBook.getTitle()).isEqualTo("Test Book");
        assertThat(createdBook.getPublisherId()).isEqualTo(1L);
        assertThat(createdBook.getCategoryId()).isEqualTo(1L);

        verify(publisherRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(Mockito.any(BookEntity.class));
    }

    @Test
    void testUpdateBook() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Updated Test Book");
        bookDto.setAuthor("Updated Author");
        bookDto.setPrice(200.0);
        bookDto.setIsbn("0987654321");
        bookDto.setPublisherId(1L);
        bookDto.setCategoryId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(updateBook));
        when(publisherRepository.existsById(1L)).thenReturn(true);
        when(categoryRepository.existsById(1L)).thenReturn(true);
        when(bookRepository.save(Mockito.any(BookEntity.class))).thenReturn(updateBook);

        ResponseEntity<?> result = bookService.updateBook(1L, bookDto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void testDeleteBook() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);

        ResponseEntity<?> result = bookService.deleteBook(1L);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testGetBook() {
        when(bookRepository.findByIdEager(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);
        ResponseEntity<?> result = bookService.getBook(1L);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isInstanceOf(BookDto.class);

        BookDto bookDto = (BookDto) result.getBody();
        assertThat(bookDto.getTitle()).isEqualTo("Test Book");
        assertThat(bookDto.getPublisherId()).isEqualTo(1L);
        assertThat(bookDto.getCategoryId()).isEqualTo(1L);
    }
}
