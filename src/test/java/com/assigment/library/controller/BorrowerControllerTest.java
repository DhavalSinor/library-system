package com.assigment.library.controller;

import com.assigment.library.dto.request.BorrowerCreateRequest;
import com.assigment.library.entity.Borrower;
import com.assigment.library.entity.Book;
import com.assigment.library.service.BorrowerService;
import com.assigment.library.service.LibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BorrowerController.class)
@Import(BorrowerControllerTestConfig.class)
class BorrowerControllerTest {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper mapper;
    @Autowired private BorrowerService borrowerService;
    @Autowired private LibraryService libraryService;

    @Test
    void registerBorrower_returnsSaved() throws Exception {
        BorrowerCreateRequest req = new BorrowerCreateRequest();
        req.setName("John Doe");
        Borrower borrower = new Borrower();
        borrower.setId(UUID.randomUUID());
        borrower.setName("John Doe");

        when(borrowerService.addBorrower(any())).thenReturn(borrower);

        mvc.perform(post("/api/borrowers")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void borrowBook_returnsBook() throws Exception {
        UUID borrowerId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Test Book");

        when(libraryService.borrowBook(any(), any())).thenReturn(book);

        mvc.perform(post("/api/borrowers/" + borrowerId + "/borrow/" + bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"));
    }

    @Test
    void returnBook_returnsBook() throws Exception {
        UUID borrowerId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Returned Book");

        when(libraryService.returnBook(any(), any())).thenReturn(book);

        mvc.perform(post("/api/borrowers/" + borrowerId + "/return/" + bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Returned Book"));
    }
}
