package com.musicstream.domain.antifraud.service;

import com.musicstream.domain.account.model.Account;
import com.musicstream.domain.antifraud.model.FraudViolation;
import com.musicstream.domain.antifraud.model.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Regra de antifraude: cartão de crédito deve estar ativo.
 * Linguagem ubíqua: cartao-nao-ativo
 */
@Component
public class CardNotActiveRule implements FraudRule {

    @Override
    public Optional<FraudViolation> evaluate(
            Account account,
            BigDecimal amount,
            String merchant,
            List<Transaction> recentTransactions) {

        if (!account.hasValidCreditCard()) {
            return Optional.of(FraudViolation.CARD_NOT_ACTIVE);
        }
        return Optional.empty();
    }
}
