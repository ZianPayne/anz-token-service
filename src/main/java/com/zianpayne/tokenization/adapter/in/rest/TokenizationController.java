package com.zianpayne.tokenization.adapter.in.rest;

import com.zianpayne.tokenization.adapter.in.mapper.TokenizationMapper;
import com.zianpayne.tokenization.adapter.in.rest.model.AccountNumbers;
import com.zianpayne.tokenization.adapter.in.rest.model.Tokens;
import com.zianpayne.tokenization.application.port.in.TokenUseCase;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class TokenizationController implements TokenApi {
    private static final Logger log = LoggerFactory.getLogger(TokenizationController.class);
    private final TokenUseCase tokenUseCase;
    private final TokenizationMapper mapper;

    public TokenizationController(TokenUseCase tokenUseCase, TokenizationMapper mapper) {
        this.tokenUseCase = tokenUseCase;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Tokens> tokenize(@Valid AccountNumbers restAccountNumbers) {
        log.info("/tokenize request received: {} account numbers", restAccountNumbers.getAccountNumbers().size());
        var domainAccountNumbers = mapper.toDomainAccountNumbers(restAccountNumbers);
        var domainTokens = tokenUseCase.tokenize(domainAccountNumbers);
        var restTokens = mapper.toRestTokens(domainTokens);
        log.info("/tokenize response: {} tokens", restTokens.getTokens().size());
        return ResponseEntity.ok(restTokens);
    }

    @Override
    public ResponseEntity<AccountNumbers> detokenize(@Valid Tokens restTokens) {
        log.info("/detokenize request received: {} tokens", restTokens.getTokens().size());
        var domainTokens = mapper.toDomainTokens(restTokens);
        var domainAccountNumbers = tokenUseCase.detokenize(domainTokens);
        var restAccountNumbers = mapper.toRestAccountNumbers(domainAccountNumbers);
        log.info("/detokenize response: {} account numbers", restAccountNumbers.getAccountNumbers().size());
        return ResponseEntity.ok(restAccountNumbers);
    }
}
