package com.zianpayne.tokenization.domain.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record AccountNumbers(List<AccountNumber> accountNumbers) {
    public AccountNumbers {
        Objects.requireNonNull(accountNumbers, "accountNumbers");
        if (accountNumbers.isEmpty()) {
            throw new IllegalArgumentException("accountNumbers must not be empty");
        }
    }

    public List<AccountNumber> asList() {
        return Collections.unmodifiableList(accountNumbers);
    }
}