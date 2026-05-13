package com.zianpayne.tokenization.application.port.out;

import com.zianpayne.tokenization.domain.model.AccountNumber;

import java.util.Optional;

public interface AccountPort {
    void save(AccountNumber accountNumber);

    Optional<AccountNumber> findByValue(String value);

    boolean exists(AccountNumber accountNumber);
}
