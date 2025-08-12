package com.assigment.library.controller;

import com.assigment.library.service.BorrowerService;
import com.assigment.library.service.LibraryService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class BorrowerControllerTestConfig {
    @Bean
    public BorrowerService borrowerService() {
        return Mockito.mock(BorrowerService.class);
    }
    @Bean
    public LibraryService libraryService() {
        return Mockito.mock(LibraryService.class);
    }
}
