package com.zianpayne.tokenization.application.port.in;

import com.zianpayne.tokenization.domain.model.AccountNumbers;
import com.zianpayne.tokenization.domain.model.Tokens;

public interface TokenUseCase {
    Tokens tokenize(AccountNumbers accountNumbers);

    AccountNumbers detokenize(Tokens tokens);
}
