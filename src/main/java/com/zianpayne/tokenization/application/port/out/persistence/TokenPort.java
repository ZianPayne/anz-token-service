package com.zianpayne.tokenization.application.port.out.persistence;

import com.zianpayne.tokenization.domain.model.AccountNumber;
import com.zianpayne.tokenization.domain.model.Token;

import java.util.Optional;

public interface TokenPort {
    void save(AccountNumber accountNumber, Token token);

    Optional<Token> findByAccountNumber(AccountNumber accountNumber);

    Optional<AccountNumber> findAccountNumberByToken(Token token);
}
