package com.zianpayne.tokenization.adapter.out.persistence;

import com.zianpayne.tokenization.domain.model.AccountNumber;
import com.zianpayne.tokenization.domain.model.Token;
import com.zianpayne.tokenization.application.port.out.persistence.TokenPort;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryTokenRepository implements TokenPort {
    private final Map<String, String> accountToTokenMap = new HashMap<>();
    private final Map<String, String> tokenToAccountMap = new HashMap<>();

    @Override
    public void save(AccountNumber accountNumber, Token token) {
        String account = accountNumber.value();
        String tokenValue = token.value();
        accountToTokenMap.put(account, tokenValue);
        tokenToAccountMap.put(tokenValue, account);
    }

    @Override
    public Optional<Token> findByAccountNumber(AccountNumber accountNumber) {
        return Optional.ofNullable(accountToTokenMap.get(accountNumber.value()))
                .map(Token::new);
    }

    @Override
    public Optional<AccountNumber> findAccountNumberByToken(Token token) {
        return Optional.ofNullable(tokenToAccountMap.get(token.value()))
                .map(AccountNumber::new);
    }
}
