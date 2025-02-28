package com.bookstore.repository.custom;

import com.bookstore.dto.BookFilterDto;
import com.bookstore.model.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookRepositoryCustom {
    Page<BookEntity> getDataWithPagination(Pageable pageable, BookFilterDto filter);

    boolean isBookExist(String isbn);

    Optional<BookEntity> findByIdEager(Long id);
}
