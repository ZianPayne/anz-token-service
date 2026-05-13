package com.zianpayne.tokenization.application.port.in;

import com.zianpayne.tokenization.domain.model.AccountNumber;

public interface AccountUseCase {
    void registerAccount(AccountNumber accountNumber);

    boolean accountExists(AccountNumber accountNumber);
}
