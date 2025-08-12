package com.assigment.library.service;

import com.assigment.library.dto.request.BorrowerCreateRequest;
import com.assigment.library.entity.Borrower;
import com.assigment.library.exception.classes.BadRequestException;
import com.assigment.library.repository.BorrowerRepository;
import com.assigment.library.service.impl.BorrowerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorrowerServiceTest {

    @Mock private BorrowerRepository borrowerRepo;
    private BorrowerServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new BorrowerServiceImpl(borrowerRepo);
    }

    @Test
    void addBorrower_newEmail_saves() {
        BorrowerCreateRequest req = new BorrowerCreateRequest(); req.setName("Alice"); req.setEmail("a@x.com");
        Borrower saved = new Borrower(); saved.setId(UUID.randomUUID()); saved.setName("Alice"); saved.setEmail("a@x.com");

        when(borrowerRepo.findByEmail("a@x.com")).thenReturn(Optional.empty());
        when(borrowerRepo.save(any())).thenReturn(saved);

        var result = service.addBorrower(req);
        assertThat(result.getEmail()).isEqualTo("a@x.com");
        verify(borrowerRepo).save(any());
    }

    @Test
    void addBorrower_existingEmail_throws() {
        Borrower existing = new Borrower(); existing.setEmail("a@x.com");
        when(borrowerRepo.findByEmail("a@x.com")).thenReturn(Optional.of(existing));
        BorrowerCreateRequest req = new BorrowerCreateRequest(); req.setName("X"); req.setEmail("a@x.com");

        assertThatThrownBy(() -> service.addBorrower(req)).isInstanceOf(BadRequestException.class);
    }
}
