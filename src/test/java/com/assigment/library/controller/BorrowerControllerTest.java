package com.assigment.library.controller;


import com.assigment.library.dto.request.BookCreateRequest;
import com.assigment.library.entity.Book;
import com.assigment.library.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BorrowerControllerTest {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper mapper;
    @MockBean private BookService bookService;

    @Test
    void addBook_returnsSaved() throws Exception {
        BookCreateRequest req = new BookCreateRequest(); req.setIsbn("X"); req.setTitle("T"); req.setAuthor("A");
        Book book = new Book(); book.setId(UUID.randomUUID()); book.setIsbn("X"); book.setTitle("T"); book.setAuthor("A");

        when(bookService.addBook(any())).thenReturn(book);

        mvc.perform(post("/api/books")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("X"))
                .andExpect(jsonPath("$.title").value("T"));
    }

    @Test
    void listBooks_returnsList() throws Exception {
        Book b = new Book(); b.setId(UUID.randomUUID()); b.setTitle("T1");
        when(bookService.listBooks()).thenReturn(List.of(b));

        mvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("T1"));
    }
}

