package com.assigment.library.service.impl;
import com.assigment.library.entity.Book;
import com.assigment.library.entity.Borrower;
import com.assigment.library.exception.classes.BadRequestException;
import com.assigment.library.exception.classes.RecordNotFoundException;
import com.assigment.library.repository.BookRepository;
import com.assigment.library.repository.BorrowerRepository;
import com.assigment.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Borrow/return is transactional and uses SELECT FOR UPDATE (pessimistic lock)
 * to prevent two borrowers from borrowing the same book id simultaneously.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepo;
    private final BorrowerRepository borrowerRepo;

    @Override
    @Transactional
    public Book borrowBook(UUID borrowerId, UUID bookId) {
        Borrower borrower = borrowerRepo.findById(borrowerId)
                .orElseThrow(() -> new RecordNotFoundException("Borrower not found"));

        Book book = bookRepo.findByIdForUpdate(bookId)
                .orElseThrow(() -> new RecordNotFoundException("Book not found"));

        if (book.getStatus() == Book.Status.BORROWED) {
            throw new BadRequestException("Book is already borrowed");
        }

        book.setStatus(Book.Status.BORROWED);
        book.setBorrowerId(borrower.getId());
        Book saved = bookRepo.save(book);
        log.info("Book {} borrowed by {}", bookId, borrowerId);
        return saved;
    }

    @Override
    @Transactional
    public Book returnBook(UUID borrowerId, UUID bookId) {
        Borrower borrower = borrowerRepo.findById(borrowerId)
                .orElseThrow(() -> new RecordNotFoundException("Borrower not found"));

        Book book = bookRepo.findByIdForUpdate(bookId)
                .orElseThrow(() -> new RecordNotFoundException("Book not found"));

        if (book.getStatus() == Book.Status.AVAILABLE) {
            throw new BadRequestException("Book is not borrowed");
        }

        if (book.getBorrowerId() == null || !book.getBorrowerId().equals(borrower.getId())) {
            throw new BadRequestException("This borrower did not borrow the book");
        }

        book.setStatus(Book.Status.AVAILABLE);
        book.setBorrowerId(null);
        Book saved = bookRepo.save(book);
        log.info("Book {} returned by {}", bookId, borrowerId);
        return saved;
    }
}
