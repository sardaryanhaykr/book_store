package com.bookstore;

import com.bookstore.model.CategoryEntity;
import com.bookstore.model.PublisherEntity;
import com.bookstore.repository.CategoryRepository;
import com.bookstore.repository.PublisherRepository;
import com.bookstore.service.BookServiceImpl;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private PublisherRepository publisherRepository;

    @MockBean
    private BookServiceImpl bookServiceImpl;

    private PublisherEntity publisher;
    private CategoryEntity category;

    @BeforeEach
    public void setUp() {
        publisher = new PublisherEntity();
        publisher.setId(1L);
        publisher.setName("Test Publisher");
        publisher.setAddress("Test Address");
        publisher.setPhone("1234567890");
        publisher.setEmail("test123@mail.com");

        category = new CategoryEntity();
        category.setId(1L);
        category.setName("Test Category");
    }

    @Test
    void testBookCreation() throws Exception {
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        JSONObject bookJson = new JSONObject();
        bookJson.put("title", "Test Book");
        bookJson.put("author", "Test Author");
        bookJson.put("isbn", "1234567890");
        bookJson.put("categoryId", 1L);
        bookJson.put("publisherId", 1L);
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson.toString()))
                .andExpect(status().is2xxSuccessful());

    }
}