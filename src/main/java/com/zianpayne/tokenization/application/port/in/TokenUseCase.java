package com.zianpayne.tokenization.application.port.in;

import com.zianpayne.tokenization.domain.model.AccountNumbers;
import com.zianpayne.tokenization.domain.model.Tokens;
import jakarta.validation.Valid;

public interface TokenUseCase {
    Tokens tokenize(@Valid AccountNumbers accountNumbers);

    AccountNumbers detokenize(@Valid Tokens tokens);
}
