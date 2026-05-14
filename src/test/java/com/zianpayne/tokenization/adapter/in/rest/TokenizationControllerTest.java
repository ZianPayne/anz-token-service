package com.zianpayne.tokenization.adapter.in.rest;

import com.zianpayne.tokenization.adapter.in.exception.TokenizationExceptionHandler;
import com.zianpayne.tokenization.adapter.in.mapper.TokenizationMapper;
import com.zianpayne.tokenization.adapter.in.rest.model.AccountNumber;
import com.zianpayne.tokenization.adapter.in.rest.model.AccountNumbers;
import com.zianpayne.tokenization.adapter.in.rest.model.Token;
import com.zianpayne.tokenization.adapter.in.rest.model.Tokens;
import com.zianpayne.tokenization.application.port.in.TokenUseCase;
import com.zianpayne.tokenization.testutil.TokenHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TokenizationController.class)
@Import(TokenizationExceptionHandler.class)
class TokenizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenUseCase tokenUseCase;

    @MockBean
    private TokenizationMapper mapper;

    @Test
    void tokenizeReturnsBadRequestWhenMapperFails() throws Exception {
        when(mapper.toDomainAccountNumbers(any(AccountNumbers.class)))
                .thenThrow(new IllegalArgumentException("bad"));

        mockMvc.perform(post("/tokenize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumbers\":[{\"value\":\"4111-1111-1111-1111\"}]}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"));
    }

    @Test
    void tokenizeReturnsBadRequestWhenMultipleAccountNumbersFail() throws Exception {
        when(mapper.toDomainAccountNumbers(any(AccountNumbers.class)))
                .thenThrow(new IllegalArgumentException("bad"));

        mockMvc.perform(post("/tokenize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumbers\":[{\"value\":\"4111-1111-1111-1111\"},{\"value\":\"4444-3333-2222-1111\"}]}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"));
    }

    @Test
    void tokenizeReturnsTokensForSingleAccount() throws Exception {
        var domainAccounts = TokenHelper.domainAccountNumbers(TokenHelper.DEFAULT_ACCOUNT);
        var domainTokens = TokenHelper.domainTokens(TokenHelper.DEFAULT_TOKEN);
        Tokens restTokens = new Tokens().addTokensItem(new Token().value(TokenHelper.DEFAULT_TOKEN));

        when(mapper.toDomainAccountNumbers(any(AccountNumbers.class))).thenReturn(domainAccounts);
        when(tokenUseCase.tokenize(domainAccounts)).thenReturn(domainTokens);
        when(mapper.toRestTokens(domainTokens)).thenReturn(restTokens);

        mockMvc.perform(post("/tokenize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumbers\":[{\"value\":\"4111-1111-1111-1111\"}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokens[0].value").value(TokenHelper.DEFAULT_TOKEN));
    }

    @Test
    void tokenizeReturnsTokensForMultipleAccounts() throws Exception {
        var domainAccounts = TokenHelper.domainAccountNumbers(
                TokenHelper.DEFAULT_ACCOUNT,
                4_444_333_322_221_111L);
        var domainTokens = TokenHelper.domainTokens(
                TokenHelper.DEFAULT_TOKEN,
                "B1234567890123456789012345678901");
        Tokens restTokens = new Tokens()
                .addTokensItem(new Token().value(TokenHelper.DEFAULT_TOKEN))
                .addTokensItem(new Token().value("B1234567890123456789012345678901"));

        when(mapper.toDomainAccountNumbers(any(AccountNumbers.class))).thenReturn(domainAccounts);
        when(tokenUseCase.tokenize(domainAccounts)).thenReturn(domainTokens);
        when(mapper.toRestTokens(domainTokens)).thenReturn(restTokens);

        mockMvc.perform(post("/tokenize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumbers\":[{\"value\":\"4111-1111-1111-1111\"},{\"value\":\"4444-3333-2222-1111\"}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokens[0].value").value(TokenHelper.DEFAULT_TOKEN))
                .andExpect(jsonPath("$.tokens[1].value").value("B1234567890123456789012345678901"));
    }

    @Test
    void detokenizeReturnsNotFoundWhenTokenMissing() throws Exception {
                var domainTokens = TokenHelper.domainTokens(TokenHelper.DEFAULT_TOKEN);

        when(mapper.toDomainTokens(any(Tokens.class))).thenReturn(domainTokens);
        when(tokenUseCase.detokenize(domainTokens))
                .thenThrow(new NoSuchElementException("missing"));

        mockMvc.perform(post("/detokenize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tokens\":[{\"value\":\"A1234567890123456789012345678901\"}]}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }

    @Test
    void detokenizeReturnsNotFoundWhenSomeTokensMissing() throws Exception {
        var domainTokens = TokenHelper.domainTokens(
                TokenHelper.DEFAULT_TOKEN,
                "B1234567890123456789012345678901");

        when(mapper.toDomainTokens(any(Tokens.class))).thenReturn(domainTokens);
        when(tokenUseCase.detokenize(domainTokens))
                .thenThrow(new NoSuchElementException("missing"));

        mockMvc.perform(post("/detokenize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tokens\":[{\"value\":\"A1234567890123456789012345678901\"},{\"value\":\"B1234567890123456789012345678901\"}]}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }

    @Test
    void detokenizeReturnsAccountsForSingleToken() throws Exception {
        var domainTokens = TokenHelper.domainTokens(TokenHelper.DEFAULT_TOKEN);
        var domainAccounts = TokenHelper.domainAccountNumbers(TokenHelper.DEFAULT_ACCOUNT);
        AccountNumbers restAccounts = new AccountNumbers()
                .addAccountNumbersItem(new AccountNumber().value("4111-1111-1111-1111"));

        when(mapper.toDomainTokens(any(Tokens.class))).thenReturn(domainTokens);
        when(tokenUseCase.detokenize(domainTokens)).thenReturn(domainAccounts);
        when(mapper.toRestAccountNumbers(domainAccounts)).thenReturn(restAccounts);

        mockMvc.perform(post("/detokenize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tokens\":[{\"value\":\"A1234567890123456789012345678901\"}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumbers[0].value").value("4111-1111-1111-1111"));
    }

    @Test
    void detokenizeReturnsAccountsForMultipleTokens() throws Exception {
        var domainTokens = TokenHelper.domainTokens(
                TokenHelper.DEFAULT_TOKEN,
                "B1234567890123456789012345678901");
        var domainAccounts = TokenHelper.domainAccountNumbers(
                TokenHelper.DEFAULT_ACCOUNT,
                4_444_333_322_221_111L);
        AccountNumbers restAccounts = new AccountNumbers()
                .addAccountNumbersItem(new AccountNumber().value("4111-1111-1111-1111"))
                .addAccountNumbersItem(new AccountNumber().value("4444-3333-2222-1111"));

        when(mapper.toDomainTokens(any(Tokens.class))).thenReturn(domainTokens);
        when(tokenUseCase.detokenize(domainTokens)).thenReturn(domainAccounts);
        when(mapper.toRestAccountNumbers(domainAccounts)).thenReturn(restAccounts);

        mockMvc.perform(post("/detokenize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tokens\":[{\"value\":\"A1234567890123456789012345678901\"},{\"value\":\"B1234567890123456789012345678901\"}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumbers[0].value").value("4111-1111-1111-1111"))
                .andExpect(jsonPath("$.accountNumbers[1].value").value("4444-3333-2222-1111"));
    }

    @Test
    void tokenizeReturnsServerErrorWhenDbFails() throws Exception {
                var domain = TokenHelper.domainAccountNumbers(TokenHelper.DEFAULT_ACCOUNT);

        when(mapper.toDomainAccountNumbers(any(AccountNumbers.class))).thenReturn(domain);
        when(tokenUseCase.tokenize(domain))
                .thenThrow(new DataAccessResourceFailureException("db"));

        mockMvc.perform(post("/tokenize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumbers\":[{\"value\":\"4111-1111-1111-1111\"}]}"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("INTERNAL_SERVER_ERROR"));
    }
}
