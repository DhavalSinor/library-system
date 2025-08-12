package com.assigment.library.exception.dto;

import java.time.Instant;

public record ApiErrorResponse(Instant timestamp, String message, String details) {
}
