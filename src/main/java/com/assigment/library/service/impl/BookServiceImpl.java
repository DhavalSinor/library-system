package com.assigment.library.service.impl;

import com.assigment.library.dto.request.BookCreateRequest;
import com.assigment.library.entity.Book;
import com.assigment.library.exception.classes.BadRequestException;
import com.assigment.library.repository.BookRepository;
import com.assigment.library.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Handles book registration and retrieval.
 * Enforces ISBN -> title/author consistency.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;

    @Override
    @Transactional
    public Book addBook(BookCreateRequest request) {
        // Check ISBN consistency
        List<Book> existing = bookRepo.findByIsbn(request.getIsbn());
        if (!existing.isEmpty()) {
            Book e = existing.get(0);
            if (!e.getTitle().equals(request.getTitle()) || !e.getAuthor().equals(request.getAuthor())) {
                log.warn("ISBN conflict: {} exists with different title/author", request.getIsbn());
                throw new BadRequestException("ISBN already exists with different title/author");
            }
        }
        Book b = new Book();
        b.setIsbn(request.getIsbn());
        b.setTitle(request.getTitle());
        b.setAuthor(request.getAuthor());
        b.setStatus(Book.Status.AVAILABLE);
        Book saved = bookRepo.save(b);
        log.info("Added book id={}, isbn={}", saved.getId(), saved.getIsbn());
        return saved;
    }

    @Override
    public List<Book> listBooks() {
        return bookRepo.findAll();
    }
}
