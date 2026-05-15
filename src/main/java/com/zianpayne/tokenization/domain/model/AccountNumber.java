package com.zianpayne.tokenization.domain.model;

import java.util.Objects;

public record AccountNumber(Long value) {
    public AccountNumber {
        Objects.requireNonNull(value, "value");
    }
}