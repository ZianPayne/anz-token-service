package com.zianpayne.tokenization.domain.model;

import java.util.Objects;
import java.util.regex.Pattern;

public final class AccountNumber {
    private static final Pattern PATTERN = Pattern.compile("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$");

    private final String value;

    public AccountNumber(String value) {
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

    private static void validate(String value) {
        if (!PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("AccountNumber must match ####-####-####-####");
        }
    }
}
