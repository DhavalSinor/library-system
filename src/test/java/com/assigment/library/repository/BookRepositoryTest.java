package com.assigment.library.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

import com.assigment.library.entity.Book;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @TestConfiguration
    static class TestAuditorConfig {
        @Bean
        public AuditorAware<String> auditorProvider() {
            return () -> Optional.of("test-user");
        }
    }

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
    }

    @Test
    void findTestAll() {
        Book book1 = new Book();
        book1.setAuthor("dhaval");
        book1.setTitle("My Life");
        book1.setIsbn("201");

        Book book2 = new Book();
        book2.setAuthor("Rajesh");
        book2.setTitle("My End");
        book2.setIsbn("202");

        bookRepository.saveAll(Arrays.asList(book1, book2));

        assertEquals(2, bookRepository.findAll().size());
    }

    @Test
    void findTestById() {
        Book book = new Book();
        book.setAuthor("dhaval");
        book.setTitle("My Life");
        book.setIsbn("201");

        bookRepository.save(book);

        Optional<Book> existBook = bookRepository.findById(book.getId());
        assertTrue(existBook.isPresent());
    }
}
