package com.assigment.library.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder

public class BookCreateResponse {
    private UUID id;
    private String isbn;
    private String title;
    private String author;
    private String status;
    private UUID borrowerId;
    private Instant createdAt;
}
