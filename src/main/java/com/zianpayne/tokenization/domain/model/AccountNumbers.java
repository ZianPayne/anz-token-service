package com.zianpayne.tokenization.domain.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class AccountNumbers {
    private final List<AccountNumber> accountNumbers;

    public AccountNumbers(List<AccountNumber> accountNumbers) {
        this.accountNumbers = Collections.unmodifiableList(Objects.requireNonNull(accountNumbers, "accountNumbers"));
        if (this.accountNumbers.isEmpty()) {
            throw new IllegalArgumentException("accountNumbers must not be empty");
        }
    }

    public List<AccountNumber> asList() {
        return accountNumbers;
    }

    @Override
    public String toString() {
        return "AccountNumbers{" + accountNumbers + '}';
    }
}
