package com.zianpayne.tokenization.application.service;

import com.zianpayne.tokenization.application.port.in.TokenUseCase;
import com.zianpayne.tokenization.application.port.out.persistence.TokenPort;
import com.zianpayne.tokenization.domain.model.AccountNumber;
import com.zianpayne.tokenization.domain.model.AccountNumbers;
import com.zianpayne.tokenization.domain.model.Token;
import com.zianpayne.tokenization.domain.model.Tokens;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TokenizationService implements TokenUseCase {
    private final TokenPort tokenPort;

    public TokenizationService(TokenPort tokenPort) {
        this.tokenPort = tokenPort;
    }

    @Override
    public Tokens tokenize(AccountNumbers accountNumbers) {
        List<Token> tokens = accountNumbers.asList().stream()
                .map(this::tokenizeSingle)
                .toList();

        return new Tokens(tokens);
    }

    @Override
    public AccountNumbers detokenize(Tokens tokens) {
        List<AccountNumber> accountNumbers = tokens.asList().stream()
                .map(this::detokenizeSingle)
                .toList();

        return new AccountNumbers(accountNumbers);
    }

    private Token generateToken() {
        String generated = UUID.randomUUID().toString().replace("-", "");
        return new Token(generated);
    }

    private Token tokenizeSingle(AccountNumber accountNumber) {
        return tokenPort.findByAccountNumber(accountNumber)
                .orElseGet(() -> {
                    Token token = generateUniqueToken();
                    tokenPort.save(accountNumber, token);
                    return token;
                });
    }

    private AccountNumber detokenizeSingle(Token token) {
        return tokenPort.findAccountNumberByToken(token)
                .orElseThrow(() -> new NoSuchElementException("Token not found: " + token.value()));
    }

    private Token generateUniqueToken() {
        Token token;
        do {
            token = generateToken();
        } while (tokenPort.findAccountNumberByToken(token).isPresent());
        return token;
    }
}
