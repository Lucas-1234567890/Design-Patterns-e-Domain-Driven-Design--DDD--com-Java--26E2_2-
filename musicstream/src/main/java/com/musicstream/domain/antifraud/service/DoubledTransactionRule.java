package com.musicstream.domain.antifraud.service;

import com.musicstream.domain.account.model.Account;
import com.musicstream.domain.antifraud.model.FraudViolation;
import com.musicstream.domain.antifraud.model.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Regra de antifraude: não mais de 2 transações similares
 * (mesmo valor e comerciante) em intervalo de 2 minutos.
 * Linguagem ubíqua: transacao-duplicada
 */
@Component
public class DoubledTransactionRule implements FraudRule {

    private static final int MAX_SIMILAR_TRANSACTIONS = 2;
    private static final int INTERVAL_IN_MINUTES = 2;

    @Override
    public Optional<FraudViolation> evaluate(
            Account account,
            BigDecimal amount,
            String merchant,
            List<Transaction> recentTransactions) {

        LocalDateTime now = LocalDateTime.now();

        // Cria uma transação temporária para comparação
        Transaction incoming = Transaction.approved(account.getId(), amount, merchant);

        long similarTransactions = recentTransactions.stream()
                .filter(t -> t.occurredWithinMinutesOf(now, INTERVAL_IN_MINUTES))
                .filter(incoming::isSimilarTo)
                .count();

        if (similarTransactions >= MAX_SIMILAR_TRANSACTIONS) {
            return Optional.of(FraudViolation.DOUBLED_TRANSACTION);
        }
        return Optional.empty();
    }
}
