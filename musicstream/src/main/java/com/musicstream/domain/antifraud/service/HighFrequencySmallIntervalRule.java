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
 * Regra de antifraude: não mais de 3 transações em um intervalo de 2 minutos.
 * Linguagem ubíqua: alta-frequencia-pequeno-intervalo
 */
@Component
public class HighFrequencySmallIntervalRule implements FraudRule {

    private static final int MAX_TRANSACTIONS_IN_INTERVAL = 3;
    private static final int INTERVAL_IN_MINUTES = 2;

    @Override
    public Optional<FraudViolation> evaluate(
            Account account,
            BigDecimal amount,
            String merchant,
            List<Transaction> recentTransactions) {

        LocalDateTime now = LocalDateTime.now();

        long transactionsInInterval = recentTransactions.stream()
                .filter(t -> t.occurredWithinMinutesOf(now, INTERVAL_IN_MINUTES))
                .count();

        if (transactionsInInterval >= MAX_TRANSACTIONS_IN_INTERVAL) {
            return Optional.of(FraudViolation.HIGH_FREQUENCY_SMALL_INTERVAL);
        }
        return Optional.empty();
    }
}
