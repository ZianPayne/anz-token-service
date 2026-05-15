package com.zianpayne.tokenization.domain.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TokenTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void closeValidator() {
        validatorFactory.close();
    }

    @Test
    void acceptsValidToken() {
        Token token = new Token("A1234567890123456789012345678901");
        Set<ConstraintViolation<Token>> violations = validator.validate(token);
        assertTrue(violations.isEmpty());
    }

    @Test
    void rejectsInvalidCharacters() {
        Token token = new Token("A123456789012345678901234567890-");
        Set<ConstraintViolation<Token>> violations = validator.validate(token);
        assertEquals(1, violations.size());
    }

    @Test
    void rejectsInvalidLength() {
        Token token = new Token("ABC123");
        Set<ConstraintViolation<Token>> violations = validator.validate(token);
        assertEquals(1, violations.size());
    }
}
