package com.musicstream.application.subscription;

import com.musicstream.domain.account.exception.AccountNotFoundException;
import com.musicstream.domain.account.model.Account;
import com.musicstream.domain.account.repository.AccountRepository;
import com.musicstream.domain.antifraud.model.Transaction;
import com.musicstream.domain.antifraud.service.FraudDetectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Application Service para autorização de transações financeiras.
 * Orquestra a busca da conta e a validação de antifraude.
 * Context Mapping: usa Anticorruption Layer implícita ao traduzir dados entre Account e Antifraud.
 */
@Service
@RequiredArgsConstructor
public class TransactionApplicationService {

    private final AccountRepository accountRepository;
    private final FraudDetectionService fraudDetectionService;

    /**
     * Autoriza uma transação financeira para uma conta.
     * Aplica todas as regras de antifraude configuradas.
     */
    @Transactional
    public Transaction authorizeTransaction(Long accountId, BigDecimal amount, String merchant) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        return fraudDetectionService.authorizeTransaction(account, amount, merchant);
    }
}
