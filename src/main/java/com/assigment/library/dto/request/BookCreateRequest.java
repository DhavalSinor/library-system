package com.assigment.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookCreateRequest {
    @NotBlank
    private String isbn;
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    // getters/setters
}

