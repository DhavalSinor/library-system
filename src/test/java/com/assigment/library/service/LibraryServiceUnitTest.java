package com.assigment.library.service;

import com.assigment.library.entity.Book;
import com.assigment.library.entity.Borrower;
import com.assigment.library.exception.classes.BadRequestException;
import com.assigment.library.repository.BookRepository;
import com.assigment.library.repository.BorrowerRepository;
import com.assigment.library.service.impl.LibraryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibraryServiceUnitTest {

    @Mock private BookRepository bookRepo;
    @Mock private BorrowerRepository borrowerRepo;
    private LibraryServiceImpl libService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        libService = new LibraryServiceImpl(bookRepo, borrowerRepo);
    }

    @Test
    void borrowBook_happyPath() {
        UUID bId = UUID.randomUUID(), brId = UUID.randomUUID();
        Borrower borrower = new Borrower(); borrower.setId(brId);
        Book book = new Book(); book.setId(bId); book.setStatus(Book.Status.AVAILABLE);

        when(borrowerRepo.findById(brId)).thenReturn(Optional.of(borrower));
        when(bookRepo.findByIdForUpdate(bId)).thenReturn(Optional.of(book));
        when(bookRepo.save(any())).thenAnswer(i -> i.getArgument(0));

        var res = libService.borrowBook(brId, bId);
        assertThat(res.getStatus()).isEqualTo(Book.Status.BORROWED);
        assertThat(res.getBorrowerId()).isEqualTo(brId);
    }

    @Test
    void borrowBook_whenBookBorrowed_throws() {
        UUID bId = UUID.randomUUID(), brId = UUID.randomUUID();
        Book book = new Book(); book.setId(bId); book.setStatus(Book.Status.BORROWED);
        when(borrowerRepo.findById(brId)).thenReturn(Optional.of(new Borrower()));
        when(bookRepo.findByIdForUpdate(bId)).thenReturn(Optional.of(book));

        assertThatThrownBy(() -> libService.borrowBook(brId, bId)).isInstanceOf(BadRequestException.class);
    }

    @Test
    void returnBook_wrongBorrower_throws() {
        UUID bId = UUID.randomUUID(), brId = UUID.randomUUID();
        Borrower borrower = new Borrower(); borrower.setId(brId);
        Book book = new Book(); book.setId(bId); book.setStatus(Book.Status.BORROWED); book.setBorrowerId(UUID.randomUUID());

        when(borrowerRepo.findById(brId)).thenReturn(Optional.of(borrower));
        when(bookRepo.findByIdForUpdate(bId)).thenReturn(Optional.of(book));

        assertThatThrownBy(() -> libService.returnBook(brId, bId)).isInstanceOf(BadRequestException.class);
    }
}
