package com.musicstream.infrastructure.persistence.account;

import com.musicstream.domain.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository para a entidade Account.
 */
public interface AccountJpaRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    boolean existsByEmail(String email);
}
