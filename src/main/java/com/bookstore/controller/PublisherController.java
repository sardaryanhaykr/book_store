package com.bookstore.controller;

import com.bookstore.dto.PublisherDto;
import com.bookstore.service.PublisherServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/publishers")
public class PublisherController {
    private final PublisherServiceImpl publisherServiceImpl;

    @PostMapping
    public ResponseEntity<?> createPublisher(PublisherDto dto) {
        return publisherServiceImpl.createPublisher(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePublisher(@PathVariable Long id, PublisherDto dto) {
        return publisherServiceImpl.updatePublisher(id, dto);
    }

    @GetMapping
    public ResponseEntity<?> getPublishers() {
        return publisherServiceImpl.getPublishers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPublisher(@PathVariable Long id) {
        return publisherServiceImpl.getPublisher(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePublisher(@PathVariable Long id) {
        return publisherServiceImpl.deletePublisher(id);
    }
}
