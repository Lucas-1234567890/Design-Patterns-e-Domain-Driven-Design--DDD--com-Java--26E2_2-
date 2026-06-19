package com.musicstream.domain.account.exception;

/**
 * Exceção lançada quando uma conta não é encontrada no sistema.
 */
public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(Long accountId) {
        super("Conta não encontrada com id: " + accountId);
    }

    public AccountNotFoundException(String email) {
        super("Conta não encontrada com email: " + email);
    }
}
