package com.zianpayne.tokenization.adapter.in.rest;

import com.zianpayne.tokenization.adapter.in.mapper.TokenizationMapper;
import com.zianpayne.tokenization.adapter.in.rest.model.AccountNumbers;
import com.zianpayne.tokenization.adapter.in.rest.model.Tokens;
import com.zianpayne.tokenization.application.port.in.TokenUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenizationController implements TokenApi {
    private final TokenUseCase tokenUseCase;
    private final TokenizationMapper mapper;

    public TokenizationController(TokenUseCase tokenUseCase, TokenizationMapper mapper) {
        this.tokenUseCase = tokenUseCase;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Tokens> tokenize(AccountNumbers restAccountNumbers) {
        var domainAccountNumbers = mapper.toDomainAccountNumbers(restAccountNumbers);
        var domainTokens = tokenUseCase.tokenize(domainAccountNumbers);
        var restTokens = mapper.toRestTokens(domainTokens);
        return ResponseEntity.ok(restTokens);
    }

    @Override
    public ResponseEntity<AccountNumbers> detokenize(Tokens restTokens) {
        var domainTokens = mapper.toDomainTokens(restTokens);
        var domainAccountNumbers = tokenUseCase.detokenize(domainTokens);
        var restAccountNumbers = mapper.toRestAccountNumbers(domainAccountNumbers);
        return ResponseEntity.ok(restAccountNumbers);
    }
}
