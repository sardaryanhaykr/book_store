package com.bookstore.controller;

import com.bookstore.dto.PublisherDto;
import com.bookstore.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/publishers")
public class PublisherController {
    private final PublisherService publisherService;

    @PostMapping
    public ResponseEntity<?> createPublisher(PublisherDto dto) {
        return publisherService.createPublisher(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePublisher(@PathVariable Long id, PublisherDto dto) {
        return publisherService.updatePublisher(id, dto);
    }

    @GetMapping
    public ResponseEntity<?> getPublishers() {
        return publisherService.getPublishers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPublisher(@PathVariable Long id) {
        return publisherService.getPublisher(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePublisher(@PathVariable Long id) {
        return publisherService.deletePublisher(id);
    }
}
