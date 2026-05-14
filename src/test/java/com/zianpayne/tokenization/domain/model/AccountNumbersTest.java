package com.zianpayne.tokenization.domain.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountNumbersTest {

    @Test
    void rejectsEmptyCollection() {
        assertThrows(IllegalArgumentException.class,
                () -> new AccountNumbers(List.of()));
    }
}
