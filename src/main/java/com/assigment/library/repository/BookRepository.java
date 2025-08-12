package com.assigment.library.repository;
import com.assigment.library.entity.Book;
import org.springframework.data.jpa.repository.*;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByIsbn(String isbn);

    // used with @Transactional in service to lock row when borrowing/returning
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from Book b where b.id = :id")
    Optional<Book> findByIdForUpdate(UUID id);
}
