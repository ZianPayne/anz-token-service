package com.zianpayne.tokenization.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zianpayne.tokenization.adapter.out.persistence.entity.TokenMappingEntity;

import java.util.Optional;

public interface TokenMappingJpaRepository extends JpaRepository<TokenMappingEntity, String> {
    Optional<TokenMappingEntity> findByAccountNumber(Long accountNumber);
}
