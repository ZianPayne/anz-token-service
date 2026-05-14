package com.zianpayne.tokenization.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "token_mapping")
public class TokenMappingEntity {
    @Id
    @Column(name = "token", length = 32, nullable = false)
    private String token;

    @Column(name = "account_number", precision = 19, scale = 0, nullable = false, unique = true)
    private Long accountNumber;

    protected TokenMappingEntity() {
    }

    public TokenMappingEntity(String token, Long accountNumber) {
        this.token = token;
        this.accountNumber = accountNumber;
    }

    public String getToken() {
        return token;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }
}
