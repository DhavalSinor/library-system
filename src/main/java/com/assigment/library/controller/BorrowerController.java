package com.assigment.library.controller;

import com.assigment.library.dto.request.BorrowerCreateRequest;
import com.assigment.library.dto.response.BorrowerCreateResponse;
import com.assigment.library.dto.response.BookCreateResponse;
import com.assigment.library.entity.Borrower;
import com.assigment.library.entity.Book;
import com.assigment.library.service.BorrowerService;
import com.assigment.library.service.LibraryService;
import com.assigment.library.util.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Borrower registration and actions (borrow/return).
 */
@RestController
@RequestMapping("/api/borrowers")
@RequiredArgsConstructor
@Slf4j
public class BorrowerController {

    private final BorrowerService borrowerService;
    private final LibraryService libraryService;

    @Operation(summary = "Register a borrower")
    @PostMapping
    public ResponseEntity<BorrowerCreateResponse> registerBorrower(@Valid @RequestBody BorrowerCreateRequest req) {
        Borrower saved = borrowerService.addBorrower(req);
        return ResponseEntity.ok(Mapper.toBorrowerResponse(saved));
    }

    @Operation(summary = "Borrow a book on behalf of borrower")
    @PostMapping("/{borrowerId}/borrow/{bookId}")
    public ResponseEntity<BookCreateResponse> borrowBook(
            @PathVariable UUID borrowerId,
            @PathVariable UUID bookId) {
        Book book = libraryService.borrowBook(borrowerId, bookId);
        return ResponseEntity.ok(Mapper.toBookResponse(book));
    }

    @Operation(summary = "Return a borrowed book")
    @PostMapping("/{borrowerId}/return/{bookId}")
    public ResponseEntity<BookCreateResponse> returnBook(
            @PathVariable UUID borrowerId,
            @PathVariable UUID bookId) {
        Book book = libraryService.returnBook(borrowerId, bookId);
        return ResponseEntity.ok(Mapper.toBookResponse(book));
    }
}
