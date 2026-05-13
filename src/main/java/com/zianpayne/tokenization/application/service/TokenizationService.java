package com.zianpayne.tokenization.application.service;

import com.zianpayne.tokenization.application.port.in.TokenUseCase;
import com.zianpayne.tokenization.application.port.out.persistence.TokenPort;
import com.zianpayne.tokenization.domain.model.AccountNumber;
import com.zianpayne.tokenization.domain.model.AccountNumbers;
import com.zianpayne.tokenization.domain.model.Token;
import com.zianpayne.tokenization.domain.model.Tokens;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TokenizationService implements TokenUseCase {
    private final TokenPort tokenPort;

    public TokenizationService(TokenPort tokenPort) {
        this.tokenPort = tokenPort;
    }

    @Override
    public Tokens tokenize(AccountNumbers accountNumbers) {
        return null;
    }

    @Override
    public AccountNumbers detokenize(Tokens tokens) {
        return null;
    }

    private Token generateToken() {
        return null;
    }
}
