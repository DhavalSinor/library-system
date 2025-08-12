package com.assigment.library.service.impl;

import com.assigment.library.dto.request.BorrowerCreateRequest;
import com.assigment.library.entity.Borrower;
import com.assigment.library.exception.classes.BadRequestException;
import com.assigment.library.repository.BorrowerRepository;
import com.assigment.library.service.BorrowerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Handles borrower registration and simple checks */
@Service
@RequiredArgsConstructor
@Slf4j
public class BorrowerServiceImpl implements BorrowerService {
    private final BorrowerRepository borrowerRepo;

    @Override
    @Transactional
    public Borrower addBorrower(BorrowerCreateRequest request) {
        borrowerRepo.findByEmail(request.getEmail()).ifPresent(b -> {
            log.warn("Attempt to register existing email {}", request.getEmail());
            throw new BadRequestException("Email already registered");
        });
        Borrower borrower = new Borrower();
        borrower.setName(request.getName());
        borrower.setEmail(request.getEmail());
        Borrower saved = borrowerRepo.save(borrower);
        log.info("Registered borrower id={}, email={}", saved.getId(), saved.getEmail());
        return saved;
    }
}

