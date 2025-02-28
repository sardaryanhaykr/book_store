package com.bookstore.service;

import com.bookstore.dto.PublisherDto;
import org.springframework.http.ResponseEntity;

public interface PublisherService {
    ResponseEntity<?> createPublisher(PublisherDto dto);

    ResponseEntity<?> updatePublisher(Long id, PublisherDto dto);

    ResponseEntity<?> getPublishers();

    ResponseEntity<?> getPublisher(Long id);

    ResponseEntity<?> deletePublisher(Long id);
}
