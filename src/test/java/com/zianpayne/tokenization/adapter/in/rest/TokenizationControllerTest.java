package com.zianpayne.tokenization.adapter.in.rest;

import com.zianpayne.tokenization.adapter.in.exception.TokenizationExceptionHandler;
import com.zianpayne.tokenization.adapter.in.mapper.TokenizationMapper;
import com.zianpayne.tokenization.adapter.in.rest.model.AccountNumbers;
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
