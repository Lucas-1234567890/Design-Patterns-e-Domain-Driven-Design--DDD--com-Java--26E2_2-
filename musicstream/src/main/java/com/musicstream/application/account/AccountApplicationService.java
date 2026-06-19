package com.musicstream.application.account;

import com.musicstream.domain.account.exception.AccountAlreadyExistsException;
import com.musicstream.domain.account.exception.AccountNotFoundException;
import com.musicstream.domain.account.model.Account;
import com.musicstream.domain.account.model.CreditCard;
import com.musicstream.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application Service para operações de Conta.
 * Orquestra casos de uso sem conter lógica de domínio.
 */
@Service
@RequiredArgsConstructor
public class AccountApplicationService {

    private final AccountRepository accountRepository;

    /**
     * Cria uma nova conta de usuário.
     */
    @Transactional
    public Account createAccount(String name, String email) {
        if (accountRepository.existsByEmail(email)) {
            throw new AccountAlreadyExistsException(email);
        }
        Account account = Account.create(name, email);
        return accountRepository.save(account);
    }

    /**
     * Associa um cartão de crédito à conta.
     */
    @Transactional
    public Account assignCreditCard(Long accountId, String cardNumber, boolean active) {
        Account account = findAccountOrThrow(accountId);
        account.assignCreditCard(CreditCard.of(cardNumber, active));
        return accountRepository.save(account);
    }

    /**
     * Busca uma conta pelo id.
     */
    @Transactional(readOnly = true)
    public Account findAccount(Long accountId) {
        return findAccountOrThrow(accountId);
    }

    private Account findAccountOrThrow(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }
}
