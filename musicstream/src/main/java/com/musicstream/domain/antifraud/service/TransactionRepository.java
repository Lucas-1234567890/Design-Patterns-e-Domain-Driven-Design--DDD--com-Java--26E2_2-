package com.musicstream.domain.antifraud.service;

import com.musicstream.domain.antifraud.model.Transaction;

import java.util.List;

/**
 * Contrato do repositório de Transações.
 * Intention Revealing Interface: métodos comunicam claramente a intenção de negócio.
 */
public interface TransactionRepository {

    /**
     * Persiste uma transação no histórico.
     */
    Transaction save(Transaction transaction);

    /**
     * Busca todas as transações aprovadas de uma conta.
     */
    List<Transaction> findApprovedTransactionsByAccountId(Long accountId);
}
