package com.musicstream.domain.account.exception;

/**
 * Exceção lançada quando se tenta criar uma conta com email já existente.
 */
public class AccountAlreadyExistsException extends RuntimeException {

    public AccountAlreadyExistsException(String email) {
        super("Já existe uma conta cadastrada com o email: " + email);
    }
}
