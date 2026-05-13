package com.zianpayne.tokenization.domain.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Tokens {
    private final List<Token> tokens;

    public Tokens(List<Token> tokens) {
        this.tokens = Collections.unmodifiableList(Objects.requireNonNull(tokens, "tokens"));
        if (this.tokens.isEmpty()) {
            throw new IllegalArgumentException("tokens must not be empty");
        }
    }

    public List<Token> asList() {
        return tokens;
    }

    @Override
    public String toString() {
        return "Tokens{" + tokens + '}';
    }
}
