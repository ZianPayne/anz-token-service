package com.zianpayne.tokenization.domain.model;

import java.util.Objects;
import jakarta.validation.constraints.Pattern;

public record Token(
        @Pattern(regexp = "^[A-Za-z0-9]{32}$", message = "Token must be a 32-character alphanumeric string")
        String value) {

    public Token {
        Objects.requireNonNull(value, "value");
    }
}
