package com.zianpayne.tokenization.application.service;

import com.zianpayne.tokenization.application.port.out.persistence.TokenPort;
import com.zianpayne.tokenization.domain.model.AccountNumber;
import com.zianpayne.tokenization.domain.model.AccountNumbers;
import com.zianpayne.tokenization.domain.model.Token;
import com.zianpayne.tokenization.domain.model.Tokens;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TokenizationServiceTest {

    @Test
    void tokenizeReturnsExistingTokenWhenPresent() {
        TokenPort tokenPort = mock(TokenPort.class);
        TokenizationService service = new TokenizationService(tokenPort);
        AccountNumber accountNumber = new AccountNumber(4111111111111111L);
        Token existing = new Token("A1234567890123456789012345678901");

        when(tokenPort.findByAccountNumber(accountNumber)).thenReturn(Optional.of(existing));

        Tokens tokens = service.tokenize(new AccountNumbers(List.of(accountNumber)));

        assertEquals(existing.value(), tokens.asList().get(0).value());
        verify(tokenPort, never()).save(any(), any());
    }

    @Test
    void tokenizePersistsNewTokenWhenMissing() {
        TokenPort tokenPort = mock(TokenPort.class);
        TokenizationService service = new TokenizationService(tokenPort);
        AccountNumber accountNumber = new AccountNumber(4111111111111111L);

        when(tokenPort.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());
        when(tokenPort.findAccountNumberByToken(any())).thenReturn(Optional.empty());

        service.tokenize(new AccountNumbers(List.of(accountNumber)));

        ArgumentCaptor<Token> tokenCaptor = ArgumentCaptor.forClass(Token.class);
        verify(tokenPort).save(any(AccountNumber.class), tokenCaptor.capture());
        assertEquals(32, tokenCaptor.getValue().value().length());
    }

    @Test
    void detokenizeThrowsWhenTokenMissing() {
        TokenPort tokenPort = mock(TokenPort.class);
        TokenizationService service = new TokenizationService(tokenPort);
        Token token = new Token("A1234567890123456789012345678901");

        when(tokenPort.findAccountNumberByToken(token)).thenReturn(Optional.empty());

        Tokens tokens = new Tokens(List.of(token));

        assertThrows(NoSuchElementException.class, () -> service.detokenize(tokens));
    }
}
