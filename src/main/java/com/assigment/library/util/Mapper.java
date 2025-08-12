package com.assigment.library.util;

import com.assigment.library.dto.response.BookCreateResponse;
import com.assigment.library.dto.response.BorrowerCreateResponse;
import com.assigment.library.entity.Book;
import com.assigment.library.entity.Borrower;

public final class Mapper {
    private Mapper(){}

    public static BookCreateResponse toBookResponse(Book b){
        return BookCreateResponse.builder()
                .id(b.getId())
                .isbn(b.getIsbn())
                .title(b.getTitle())
                .author(b.getAuthor())
                .status(b.getStatus().name())
                .borrowerId(b.getBorrowerId())
                .createdAt(b.getCreatedAt())
                .build();
    }

    public static BorrowerCreateResponse toBorrowerResponse(Borrower b){
        return BorrowerCreateResponse.builder()
                .id(b.getId())
                .name(b.getName())
                .email(b.getEmail())
                .createdAt(b.getCreatedAt())
                .build();
    }
}

