package com.zianpayne.tokenization.application.service;

import com.zianpayne.tokenization.application.port.in.AccountUseCase;
import com.zianpayne.tokenization.application.port.out.persistence.AccountPort;
import com.zianpayne.tokenization.domain.model.AccountNumber;
import org.springframework.stereotype.Service;

@Service
public class AccountManagementService implements AccountUseCase {
    private final AccountPort accountPort;

    public AccountManagementService(AccountPort accountPort) {
        this.accountPort = accountPort;
    }

    @Override
    public void registerAccount(AccountNumber accountNumber) {
        accountPort.save(accountNumber);
    }

    @Override
    public boolean accountExists(AccountNumber accountNumber) {
        return accountPort.exists(accountNumber);
    }
}
