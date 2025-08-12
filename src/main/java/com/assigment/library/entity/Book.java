package com.assigment.library.entity;

import com.assigment.library.baseclasses.AbstractBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Book entity — each copy is a separate row (unique UUID id).
 * ISBN must be consistent for title & author.
 */
@Entity
@Table(name = "books", indexes = {@Index(name = "idx_isbn", columnList = "isbn")})
@Getter @Setter
public class Book extends AbstractBaseEntity {

    @NotBlank
    @Column(nullable = false)
    private String isbn;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.AVAILABLE;

    public enum Status { AVAILABLE, BORROWED }

    // For simplicity we store borrower id when borrowed (nullable)
    @Column(name = "borrower_id", columnDefinition = "uuid")
    private java.util.UUID borrowerId;
}
