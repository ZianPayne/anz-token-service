package com.zianpayne.tokenization.adapter.out.persistence;

import com.zianpayne.tokenization.domain.model.AccountNumber;
import com.zianpayne.tokenization.domain.model.Token;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(TokenRepositoryAdapter.class)
class TokenRepositoryAdapterTest {

    @Autowired
    private TokenRepositoryAdapter adapter;

    @Test
    void savesAndFindsByAccountNumber() {
        AccountNumber accountNumber = new AccountNumber(4111111111111111L);
        Token token = new Token("A1234567890123456789012345678901");

        adapter.save(accountNumber, token);

        var found = adapter.findByAccountNumber(accountNumber);
        assertTrue(found.isPresent());
        assertEquals(token.value(), found.get().value());
    }

    @Test
    void findsAccountNumberByToken() {
        AccountNumber accountNumber = new AccountNumber(4111111111111111L);
        Token token = new Token("A1234567890123456789012345678901");

        adapter.save(accountNumber, token);

        var found = adapter.findAccountNumberByToken(token);
        assertTrue(found.isPresent());
        assertEquals(accountNumber.value(), found.get().value());
    }
}
