package com.bookstore.service;

import com.bookstore.dto.PublisherDto;
import com.bookstore.exception.DuplicateResourceException;
import com.bookstore.exception.IllegalArgumentExceptionCustom;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.mapper.PublisherMapper;
import com.bookstore.model.PublisherEntity;
import com.bookstore.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.bookstore.util.EmailValidationUtil.isValidEmail;

@Service
@RequiredArgsConstructor
@Transactional
public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    @Override
    public ResponseEntity<?> createPublisher(PublisherDto dto) {
        validateAdd(dto);
        PublisherEntity publisher = publisherMapper.toEntity(dto);
        publisher = publisherRepository.save(publisher);
        dto.setId(publisher.getId());
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<?> updatePublisher(Long id, PublisherDto dto) {
        PublisherEntity publisher = publisherRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publisher"));
        validateUpdate(id, dto, publisher);
        publisher.setName(dto.getName());
        publisher.setAddress(dto.getAddress());
        publisher.setPhone(dto.getPhone());
        publisher.setEmail(dto.getEmail());
        publisherRepository.save(publisher);
        return ResponseEntity.ok(dto);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getPublishers() {
        List<PublisherEntity> publishers = publisherRepository.findAll();
        if (CollectionUtils.isEmpty(publishers)) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<PublisherDto> dtos = publishers.stream().map(publisherMapper::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getPublisher(Long id) {
        PublisherEntity publisher = publisherRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publisher"));
        return ResponseEntity.ok(publisher);
    }

    @Override
    public ResponseEntity<?> deletePublisher(Long id) {
        if (!publisherRepository.existsById(id)) {
            throw new ResourceNotFoundException("Publisher");
        }
        publisherRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private void validateAdd(PublisherDto dto) {
        validateDto(dto);

        if (publisherRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email");
        }
    }

    private void validateUpdate(Long id, PublisherDto dto, PublisherEntity publisher) {
        validateDto(dto);
        if (!Objects.equals(dto.getEmail(), publisher.getEmail()) && publisherRepository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new DuplicateResourceException("Email");
        }
    }

    private void validateDto(PublisherDto dto) {
        if (dto == null) {
            throw new IllegalArgumentExceptionCustom("Publisher is required");
        }
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new IllegalArgumentExceptionCustom("Name is required");
        }
        if (dto.getAddress() == null || dto.getAddress().isEmpty()) {
            throw new IllegalArgumentExceptionCustom("Address is required");
        }
        if (dto.getPhone() == null || dto.getPhone().isEmpty()) {
            throw new IllegalArgumentExceptionCustom("Phone is required");
        }
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new IllegalArgumentExceptionCustom("Email is required");
        }
        if (!isValidEmail(dto.getEmail())) {
            throw new IllegalArgumentExceptionCustom("Invalid email format");
        }
    }
}
