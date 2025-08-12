package com.assigment.library.service;


import com.assigment.library.entity.Book;
import com.assigment.library.entity.Borrower;
import java.util.List;
import java.util.UUID;

public interface LibraryService {
    Book borrowBook(UUID borrowerId, UUID bookId);
    Book returnBook(UUID borrowerId, UUID bookId);
}

