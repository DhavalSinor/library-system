package com.assigment.library.service;

import com.assigment.library.dto.request.BookCreateRequest;
import com.assigment.library.entity.Book;
import com.assigment.library.exception.classes.BadRequestException;
import com.assigment.library.repository.BookRepository;
import com.assigment.library.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock private BookRepository bookRepo;
    private BookServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new BookServiceImpl(bookRepo);
    }

    @Test
    void addBook_newIsbn_saves() {
        BookCreateRequest req = new BookCreateRequest();
        req.setIsbn("ISBN-1"); req.setTitle("T"); req.setAuthor("A");

        Book saved = new Book(); saved.setIsbn("ISBN-1"); saved.setTitle("T"); saved.setAuthor("A"); saved.setId(UUID.randomUUID());

        when(bookRepo.findByIsbn("ISBN-1")).thenReturn(List.of());
        when(bookRepo.save(any(Book.class))).thenReturn(saved);

        Book result = service.addBook(req);
        assertThat(result.getIsbn()).isEqualTo("ISBN-1");
        verify(bookRepo).save(any());
    }

    @Test
    void addBook_conflictIsbn_throws() {
        Book existing = new Book(); existing.setIsbn("ISBN-1"); existing.setTitle("Old"); existing.setAuthor("OldA");
        BookCreateRequest req = new BookCreateRequest(); req.setIsbn("ISBN-1"); req.setTitle("New"); req.setAuthor("New");

        when(bookRepo.findByIsbn("ISBN-1")).thenReturn(List.of(existing));

        assertThatThrownBy(() -> service.addBook(req)).isInstanceOf(BadRequestException.class);
    }

    @Test
    void listBooks_returnsAll() {
        Book b1 = new Book(); b1.setId(UUID.randomUUID()); b1.setTitle("B1");
        Book b2 = new Book(); b2.setId(UUID.randomUUID()); b2.setTitle("B2");
        when(bookRepo.findAll()).thenReturn(List.of(b1,b2));
        var list = service.listBooks();
        assertThat(list).hasSize(2);
    }
}

