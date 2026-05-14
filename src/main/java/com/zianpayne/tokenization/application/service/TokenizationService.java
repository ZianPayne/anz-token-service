package com.zianpayne.tokenization.application.service;

import com.zianpayne.tokenization.application.port.in.TokenUseCase;
import com.zianpayne.tokenization.application.port.out.persistence.TokenPort;
import com.zianpayne.tokenization.domain.model.AccountNumber;
import com.zianpayne.tokenization.domain.model.AccountNumbers;
import com.zianpayne.tokenization.domain.model.Token;
import com.zianpayne.tokenization.domain.model.Tokens;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TokenizationService implements TokenUseCase {
    private static final Logger log = LoggerFactory.getLogger(TokenizationService.class);
    private final TokenPort tokenPort;

    public TokenizationService(TokenPort tokenPort) {
        this.tokenPort = tokenPort;
    }

    @Override
    public Tokens tokenize(AccountNumbers accountNumbers) {
        log.debug("Tokenize request received with {} account numbers", accountNumbers.asList().size());
        List<Token> tokens = accountNumbers.asList().stream()
                .map(this::tokenizeSingle)
                .toList();

        log.debug("Tokenize completed with {} tokens", tokens.size());
        return new Tokens(tokens);
    }

    @Override
    public AccountNumbers detokenize(Tokens tokens) {
        log.debug("Detokenize request received with {} tokens", tokens.asList().size());
        List<AccountNumber> accountNumbers = tokens.asList().stream()
                .map(this::detokenizeSingle)
                .toList();

        log.debug("Detokenize completed with {} account numbers", accountNumbers.size());
        return new AccountNumbers(accountNumbers);
    }

    private Token generateToken() {
        String generated = UUID.randomUUID().toString().replace("-", "");
        return new Token(generated);
    }

    private Token tokenizeSingle(AccountNumber accountNumber) {
        return tokenPort.findByAccountNumber(accountNumber)
                .map(existing -> {
                    log.debug("Account already tokenized: {}", accountNumber.value());
                    return existing;
                })
                .orElseGet(() -> {
                    Token token = generateUniqueToken();
                    tokenPort.save(accountNumber, token);
                    log.debug("Token created for account: {}", accountNumber.value());
                    return token;
                });
    }

    private AccountNumber detokenizeSingle(Token token) {
        return tokenPort.findAccountNumberByToken(token)
                .orElseThrow(() -> {
                    log.warn("Token not found: {}", token.value());
                    return new NoSuchElementException("Token not found: " + token.value());
                });
    }

    private Token generateUniqueToken() {
        Token token;
        do {
            token = generateToken();
        } while (tokenPort.findAccountNumberByToken(token).isPresent());
        return token;
    }
}
