package com.zianpayne.tokenization.adapter.in.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountValidatorTest {

    @Test
    void toDomainValueAcceptsValidPattern() {
        long value = AccountValidator.toDomainValue("4111-1111-1111-1111");
        assertEquals(4111111111111111L, value);
    }

    @Test
    void toDomainValueRejectsInvalidPattern() {
        assertThrows(IllegalArgumentException.class,
                () -> AccountValidator.toDomainValue("4111111111111111"));
    }

    @Test
    void toRestValueFormatsLeadingZeros() {
        String formatted = AccountValidator.toRestValue(1L);
        assertEquals("0000-0000-0000-0001", formatted);
    }

    @Test
    void toRestValueRejectsNegative() {
        assertThrows(IllegalArgumentException.class, () -> AccountValidator.toRestValue(-1L));
    }

    @Test
    void toRestValueRejectsTooLarge() {
        assertThrows(IllegalArgumentException.class,
                () -> AccountValidator.toRestValue(10_000_000_000_000_000L));
    }
}
