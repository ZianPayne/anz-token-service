package com.zianpayne.tokenization.adapter.in.exception;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TokenizationExceptionHandlerTest {

    private final TokenizationExceptionHandler handler = new TokenizationExceptionHandler();

    @Test
    void mapsIllegalArgumentToBadRequest() {
        var response = handler.handleIllegalArgument(new IllegalArgumentException("bad"));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("BAD_REQUEST", response.getBody().getCode());
    }

    @Test
    void mapsNotFoundTo404() {
        var response = handler.handleNotFound(new java.util.NoSuchElementException("missing"));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("NOT_FOUND", response.getBody().getCode());
    }

    @Test
    void mapsDataAccessTo500() {
        var response = handler.handleDataAccess(new DataAccessResourceFailureException("db"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("INTERNAL_SERVER_ERROR", response.getBody().getCode());
    }
}
