package com.musicstream.domain.antifraud.service;

import com.musicstream.domain.account.model.Account;
import com.musicstream.domain.antifraud.exception.FraudViolationException;
import com.musicstream.domain.antifraud.model.FraudViolation;
import com.musicstream.domain.antifraud.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Domain Service responsável por orquestrar todas as regras de antifraude.
 * Segue o princípio Open/Closed: novas regras são injetadas sem alterar esta classe.
 * Intention Revealing Interface: o método autoriza ou lança exceção com violações claras.
 */
@Service
@RequiredArgsConstructor
public class FraudDetectionService {

    private final List<FraudRule> fraudRules;
    private final TransactionRepository transactionRepository;

    /**
     * Avalia uma transação contra todas as regras de antifraude.
     * Aprova a transação se nenhuma regra for violada.
     * Rejeita e persiste a transação se houver violações, lançando exceção com os detalhes.
     *
     * @param account  conta do usuário
     * @param amount   valor da transação
     * @param merchant comerciante envolvido
     * @throws FraudViolationException se uma ou mais regras forem violadas
     */
    public Transaction authorizeTransaction(Account account, BigDecimal amount, String merchant) {
        List<Transaction> recentTransactions = transactionRepository
                .findApprovedTransactionsByAccountId(account.getId());

        List<FraudViolation> violations = new ArrayList<>();

        for (FraudRule rule : fraudRules) {
            Optional<FraudViolation> violation = rule.evaluate(account, amount, merchant, recentTransactions);
            violation.ifPresent(violations::add);
        }

        if (!violations.isEmpty()) {
            Transaction rejected = Transaction.rejected(account.getId(), amount, merchant);
            transactionRepository.save(rejected);
            throw new FraudViolationException(violations);
        }

        Transaction approved = Transaction.approved(account.getId(), amount, merchant);
        return transactionRepository.save(approved);
    }
}
