package com.assigment.library.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class BorrowerCreateResponse {
    private UUID id;
    private String name;
    private String email;
    private Instant createdAt;
}
