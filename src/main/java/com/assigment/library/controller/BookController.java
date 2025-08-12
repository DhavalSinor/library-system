package com.assigment.library.controller;

import com.assigment.library.dto.request.BookCreateRequest;
import com.assigment.library.dto.response.BookCreateResponse;
import com.assigment.library.entity.Book;
import com.assigment.library.service.BookService;
import com.assigment.library.util.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Book endpoints: add book & list books.
 */
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Register a new book (one copy)")
    @PostMapping
    public ResponseEntity<BookCreateResponse> addBook(@Valid @RequestBody BookCreateRequest request) {
        Book saved = bookService.addBook(request);
        return ResponseEntity.ok(Mapper.toBookResponse(saved));
    }

    @Operation(summary = "List all books")
    @GetMapping
    public ResponseEntity<List<BookCreateResponse>> listBooks() {
        List<Book> books = bookService.listBooks();
        List<BookCreateResponse> resp = books.stream().map(Mapper::toBookResponse).collect(Collectors.toList());
        return ResponseEntity.ok(resp);
    }
}
