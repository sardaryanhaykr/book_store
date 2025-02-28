package com.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDto {
    private Long id;

    private String title;

    private String author;

    private Double price;

    private String isbn;

    private LocalDateTime publishedDate;

    private Long categoryId;

    private Long publisherId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return Objects.equals(id, bookDto.id) && Objects.equals(title, bookDto.title) && Objects.equals(author, bookDto.author) && Objects.equals(price, bookDto.price) && Objects.equals(isbn, bookDto.isbn) && Objects.equals(publishedDate, bookDto.publishedDate) && Objects.equals(categoryId, bookDto.categoryId) && Objects.equals(publisherId, bookDto.publisherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, price, isbn, publishedDate, categoryId, publisherId);
    }
}
