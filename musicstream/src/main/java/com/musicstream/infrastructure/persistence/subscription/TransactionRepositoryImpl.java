package com.musicstream.infrastructure.persistence.subscription;

import com.musicstream.domain.antifraud.model.Transaction;
import com.musicstream.domain.antifraud.model.TransactionStatus;
import com.musicstream.domain.antifraud.service.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementação concreta do repositório de Transações.
 */
@Repository
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionJpaRepository jpaRepository;

    @Override
    public Transaction save(Transaction transaction) {
        return jpaRepository.save(transaction);
    }

    @Override
    public List<Transaction> findApprovedTransactionsByAccountId(Long accountId) {
        return jpaRepository.findByAccountIdAndStatus(accountId, TransactionStatus.APPROVED);
    }
}
