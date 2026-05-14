package com.zianpayne.tokenization.adapter.out.persistence;

import com.zianpayne.tokenization.adapter.out.persistence.entity.TokenMappingEntity;
import com.zianpayne.tokenization.adapter.out.persistence.repository.TokenMappingJpaRepository;
import com.zianpayne.tokenization.application.port.out.persistence.TokenPort;
import com.zianpayne.tokenization.domain.model.AccountNumber;
import com.zianpayne.tokenization.domain.model.Token;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TokenRepositoryAdapter implements TokenPort {

    private final TokenMappingJpaRepository jpaRepository;

    public TokenRepositoryAdapter(TokenMappingJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Token> findByAccountNumber(AccountNumber accountNumber) {
        return jpaRepository.findByAccountNumber(accountNumber.value())
                .map(TokenMappingEntity::getToken)
                .map(Token::new);
    }

    @Override
    public Optional<AccountNumber> findAccountNumberByToken(Token token) {
        return jpaRepository.findById(token.value())
                .map(TokenMappingEntity::getAccountNumber)
                .map(AccountNumber::new);
    }

    @Override
    public void save(AccountNumber accountNumber, Token token) {
        jpaRepository.save(new TokenMappingEntity(token.value(), accountNumber.value()));
    }
}
