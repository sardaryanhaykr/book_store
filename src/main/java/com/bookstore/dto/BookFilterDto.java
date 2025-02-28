package com.bookstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BookFilterDto {
    private String title;
    private String author;
    private Double priceFrom;
    private Double priceTo;
    private LocalDateTime publishedDateFrom;
    private LocalDateTime publishedDateTo;
    private List<Long> categoryIds;
    private List<Long> publisherIds;
}
