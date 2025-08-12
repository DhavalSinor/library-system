package com.assigment.library.controller;

import com.assigment.library.service.BookService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class BookControllerTestConfig {
    @Bean
    public BookService bookService() {
        return Mockito.mock(BookService.class);
    }
}
