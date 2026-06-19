package com.musicstream.domain.antifraud.service;

import com.musicstream.domain.account.model.Account;
import com.musicstream.domain.antifraud.model.FraudViolation;
import com.musicstream.domain.antifraud.model.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Interface base para todas as regras de antifraude.
 *
 * Intention Revealing Interface: cada implementação representa UMA regra de negócio.
 * Open/Closed Principle: novas regras são adicionadas como novas classes, sem alterar as existentes.
 */
public interface FraudRule {

    /**
     * Avalia se a transação infringe esta regra de negócio.
     *
     * @param account           conta do usuário autorizando a transação
     * @param amount            valor da transação
     * @param merchant          comerciante da transação
     * @param recentTransactions histórico de transações recentes da conta
     * @return Optional com a violação detectada, ou Optional.empty() se a regra foi cumprida
     */
    Optional<FraudViolation> evaluate(
            Account account,
            BigDecimal amount,
            String merchant,
            List<Transaction> recentTransactions
    );
}
