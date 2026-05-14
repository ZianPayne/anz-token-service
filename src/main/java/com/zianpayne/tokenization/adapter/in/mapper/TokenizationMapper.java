package com.zianpayne.tokenization.adapter.in.mapper;

import com.zianpayne.tokenization.adapter.in.rest.model.AccountNumber;
import com.zianpayne.tokenization.adapter.in.rest.model.AccountNumbers;
import com.zianpayne.tokenization.adapter.in.rest.model.Token;
import com.zianpayne.tokenization.adapter.in.rest.model.Tokens;
import com.zianpayne.tokenization.adapter.in.validation.AccountValidator;
import org.springframework.stereotype.Component;

@Component
public class TokenizationMapper {

    public com.zianpayne.tokenization.domain.model.AccountNumber toDomainAccountNumber(AccountNumber restDto) {
        Long value = AccountValidator.toDomainValue(restDto.getValue());
        return new com.zianpayne.tokenization.domain.model.AccountNumber(value);
    }

    public AccountNumber toRestAccountNumber(com.zianpayne.tokenization.domain.model.AccountNumber domain) {
        var rest = new AccountNumber();
        rest.setValue(AccountValidator.toRestValue(domain.value()));
        return rest;
    }

    public com.zianpayne.tokenization.domain.model.Token toDomainToken(Token restDto) {
        return new com.zianpayne.tokenization.domain.model.Token(restDto.getValue());
    }

    public Token toRestToken(com.zianpayne.tokenization.domain.model.Token domain) {
        var rest = new Token();
        rest.setValue(domain.value());
        return rest;
    }

    public com.zianpayne.tokenization.domain.model.AccountNumbers toDomainAccountNumbers(AccountNumbers restDto) {
        var domainList = restDto.getAccountNumbers().stream()
                .map(this::toDomainAccountNumber)
                .toList();
        return new com.zianpayne.tokenization.domain.model.AccountNumbers(domainList);
    }

    public AccountNumbers toRestAccountNumbers(com.zianpayne.tokenization.domain.model.AccountNumbers domain) {
        var restDto = new AccountNumbers();
        var restList = domain.asList().stream()
                .map(this::toRestAccountNumber)
                .toList();
        restDto.setAccountNumbers(restList);
        return restDto;
    }

    public com.zianpayne.tokenization.domain.model.Tokens toDomainTokens(Tokens restDto) {
        var domainList = restDto.getTokens().stream()
                .map(this::toDomainToken)
                .toList();
        return new com.zianpayne.tokenization.domain.model.Tokens(domainList);
    }

    public Tokens toRestTokens(com.zianpayne.tokenization.domain.model.Tokens domain) {
        var restDto = new Tokens();
        var restList = domain.asList().stream()
                .map(this::toRestToken)
                .toList();
        restDto.setTokens(restList);
        return restDto;
    }
}
