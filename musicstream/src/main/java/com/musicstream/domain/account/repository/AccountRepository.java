package com.musicstream.domain.account.repository;

import com.musicstream.domain.account.model.Account;

import java.util.Optional;

/**
 * Contrato do repositório de Contas.
 * Intention Revealing Interface: o nome dos métodos comunica claramente a intenção.
 * A implementação concreta fica na camada de infraestrutura.
 */
public interface AccountRepository {

    /**
     * Persiste uma nova conta ou atualiza uma existente.
     */
    Account save(Account account);

    /**
     * Busca uma conta pelo identificador único.
     */
    Optional<Account> findById(Long id);

    /**
     * Busca uma conta pelo endereço de email.
     */
    Optional<Account> findByEmail(String email);

    /**
     * Verifica se já existe uma conta com o email informado.
     */
    boolean existsByEmail(String email);
}
