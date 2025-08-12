package com.assigment.library.service;

import com.assigment.library.dto.request.BookCreateRequest;
import com.assigment.library.entity.Book;
import java.util.List;

/**
 * Service for book operations: add & list
 */
public interface BookService {
    Book addBook(BookCreateRequest request);
    List<Book> listBooks();
}
