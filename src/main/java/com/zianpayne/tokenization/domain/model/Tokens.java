package com.zianpayne.tokenization.domain.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record Tokens(List<Token> tokens) {
    public Tokens {
        Objects.requireNonNull(tokens, "tokens");
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("tokens must not be empty");
        }
    }

    public List<Token> asList() {
        return Collections.unmodifiableList(tokens);
    }
}