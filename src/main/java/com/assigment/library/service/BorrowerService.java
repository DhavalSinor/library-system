package com.assigment.library.service;

import com.assigment.library.dto.request.BorrowerCreateRequest;
import com.assigment.library.entity.Borrower;

/** Borrower registration service */
public interface BorrowerService {
    Borrower addBorrower(BorrowerCreateRequest request);
}

