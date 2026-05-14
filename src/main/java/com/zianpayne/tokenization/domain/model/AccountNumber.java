package com.zianpayne.tokenization.domain.model;

import java.util.Objects;

public final class AccountNumber {
    private final Long value;

    public AccountNumber(Long value) {
        this.value = Objects.requireNonNull(value, "value");
    }

    public Long value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountNumber that = (AccountNumber) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "AccountNumber{" + value + '}';
    }
}
