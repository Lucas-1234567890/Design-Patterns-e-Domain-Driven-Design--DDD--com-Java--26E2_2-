package com.musicstream.infrastructure.persistence.subscription;

import com.musicstream.domain.antifraud.model.Transaction;
import com.musicstream.domain.antifraud.model.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository para a entidade Transaction.
 */
public interface TransactionJpaRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountIdAndStatus(Long accountId, TransactionStatus status);
}
