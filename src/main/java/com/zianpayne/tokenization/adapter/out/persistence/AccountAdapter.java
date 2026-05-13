package com.zianpayne.tokenization.adapter.out.persistence;

import com.zianpayne.tokenization.application.port.out.persistence.AccountPort;
import com.zianpayne.tokenization.domain.model.AccountNumber;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class InMemoryAccountRepository implements AccountPort {
    private final Set<String> accounts = new HashSet<>();

    @Override
    public void save(AccountNumber accountNumber) {
        accounts.add(accountNumber.value());
    }

    @Override
    public Optional<AccountNumber> findByValue(String value) {
        if (accounts.contains(value)) {
            return Optional.of(new AccountNumber(value));
        }
        return Optional.empty();
    }

    @Override
    public boolean exists(AccountNumber accountNumber) {
        return accounts.contains(accountNumber.value());
    }
}
