package com.zianpayne.tokenization.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TokenTest {

    @Test
    void acceptsValidToken() {
        assertDoesNotThrow(() -> new Token("A1234567890123456789012345678901"));
    }

    @Test
    void rejectsInvalidCharacters() {
        assertThrows(IllegalArgumentException.class,
                () -> new Token("A123456789012345678901234567890-"));
    }

    @Test
    void rejectsInvalidLength() {
        assertThrows(IllegalArgumentException.class, () -> new Token("ABC123"));
    }
}
