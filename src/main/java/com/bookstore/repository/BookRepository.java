package com.bookstore.repository;

import com.bookstore.model.BookEntity;
import com.bookstore.repository.custom.BookRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long>, BookRepositoryCustom {
}
