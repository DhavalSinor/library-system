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

import com.assigment.library.entity.Borrower;

@DataJpaTest
class BorrowerRepositoryTest {

    @Autowired
    private BorrowerRepository borrowerRepository;

    @TestConfiguration
    static class TestAuditorConfig {
        @Bean
        public AuditorAware<String> auditorProvider() {
            return () -> Optional.of("test-user");
        }
    }

    @AfterEach
    void tearDown() {
        borrowerRepository.deleteAll();
    }

    @Test
    void findTestAll() {
        Borrower borrower1 = new Borrower();
        borrower1.setName("Dhaval");
        borrower1.setEmail("d@gmail.com");
        
        Borrower borrower2 = new Borrower();
        borrower2.setName("Mehul");
        borrower2.setEmail("m@gmail.com");

        borrowerRepository.saveAll(Arrays.asList(borrower1, borrower2));

        assertEquals(2, borrowerRepository.findAll().size());
    }

    @Test
    void findTestById() {
    	Borrower borrower = new Borrower();
        borrower.setName("Dhaval");
        borrower.setEmail("d@gmail.com");

        borrowerRepository.save(borrower);

        Optional<Borrower> existBook = borrowerRepository.findById(borrower.getId());
        assertTrue(existBook.isPresent());
    }
}
