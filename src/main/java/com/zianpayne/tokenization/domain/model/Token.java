package com.zianpayne.tokenization.domain.model;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Token {
    private static final Pattern PATTERN = Pattern.compile("^[A-Za-z0-9]{32}$");

    private final String value;

    public Token(String value) {
        this.value = Objects.requireNonNull(value, "value");
        validate(this.value);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return value.equals(token.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Token{" + value + '}';
    }

    private static void validate(String value) {
        if (!PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Token must be a 32-character alphanumeric string");
        }
    }
}
