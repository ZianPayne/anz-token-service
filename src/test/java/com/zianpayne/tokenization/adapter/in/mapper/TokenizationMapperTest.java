package com.zianpayne.tokenization.adapter.in.mapper;

import com.zianpayne.tokenization.adapter.in.rest.model.AccountNumber;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TokenizationMapperTest {

    private final TokenizationMapper mapper = new TokenizationMapper();

    @Test
    void mapsRestAccountNumberToDomain() {
        AccountNumber rest = new AccountNumber().value("4111-1111-1111-1111");

        var domain = mapper.toDomainAccountNumber(rest);

        assertEquals(4111111111111111L, domain.value());
    }

    @Test
    void mapsDomainAccountNumberToRest() {
        var domain = new com.zianpayne.tokenization.domain.model.AccountNumber(1L);

        AccountNumber rest = mapper.toRestAccountNumber(domain);

        assertEquals("0000-0000-0000-0001", rest.getValue());
    }

    @Test
    void rejectsInvalidRestAccountNumber() {
        AccountNumber rest = new AccountNumber().value("1234");

        assertThrows(IllegalArgumentException.class, () -> mapper.toDomainAccountNumber(rest));
    }
}
