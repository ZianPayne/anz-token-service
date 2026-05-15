package com.zianpayne.tokenization.adapter.in.mapper;

import com.zianpayne.tokenization.adapter.in.rest.model.AccountNumber;
import com.zianpayne.tokenization.adapter.in.rest.model.Token;
import com.zianpayne.tokenization.adapter.in.rest.model.Tokens;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TokenizationMapperTest {

    private final TokenizationMapper mapper = new TokenizationMapper();

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void closeValidator() {
        validatorFactory.close();
    }

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

    @Test
    void tokensDtoAcceptsValidToken() {
        Token restToken = new Token().value("A1234567890123456789012345678901");
        Tokens restTokens = new Tokens().addTokensItem(restToken);

        Set<ConstraintViolation<Tokens>> violations = validator.validate(restTokens);
        assertTrue(violations.isEmpty());
    }

    @Test
    void tokensDtoRejectsInvalidCharacters() {
        Token restToken = new Token().value("A123456789012345678901234567890-");
        Tokens restTokens = new Tokens().addTokensItem(restToken);

        Set<ConstraintViolation<Tokens>> violations = validator.validate(restTokens);
        assertTrue(violations.size() >= 1);
    }

    @Test
    void tokensDtoRejectsInvalidLength() {
        Token restToken = new Token().value("ABC123");
        Tokens restTokens = new Tokens().addTokensItem(restToken);

        Set<ConstraintViolation<Tokens>> violations = validator.validate(restTokens);
        assertTrue(violations.size() >= 1);
    }
}
