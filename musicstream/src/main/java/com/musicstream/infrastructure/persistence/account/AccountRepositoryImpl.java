package com.musicstream.infrastructure.persistence.account;

import com.musicstream.domain.account.model.Account;
import com.musicstream.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implementação concreta do repositório de Contas usando Spring Data JPA.
 * Adapta a interface de domínio para o mecanismo de persistência.
 */
@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountJpaRepository jpaRepository;

    @Override
    public Account save(Account account) {
        return jpaRepository.save(account);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return jpaRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}
