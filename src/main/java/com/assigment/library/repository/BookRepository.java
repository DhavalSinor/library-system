package com.assigment.library.repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.*;

import com.assigment.library.entity.Book;

import jakarta.persistence.LockModeType;

public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByIsbn(String isbn);

    // used with @Transactional in service to lock row when borrowing/returning
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from Book b where b.id = :id")
    Optional<Book> findByIdForUpdate(UUID id);
}
